package org.shinaikessokuband.anontalk.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    @Getter
    private final Map<Long, WebSocketSession> userSessions = Collections.synchronizedMap(new HashMap<>());
    @Getter
    private final Set<Long> onlineUsers = Collections.synchronizedSet(new HashSet<>());
    // 新增：一个等待匹配的用户队列
    private final Queue<WebSocketSession> waitingUsers = new LinkedList<>();
    // 新增：用来存储用户匹配的 userId 信息
    private final Map<Long, Long> userMatches = new HashMap<>();

    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @Autowired
    public ChatWebSocketHandler(ObjectMapper jacksonObjectMapper) {
        this.jacksonObjectMapper = jacksonObjectMapper;
    }

    // 方法：将用户加入等待队列
    private void addToWaitingQueue(WebSocketSession session) {
        synchronized (waitingUsers) {
            waitingUsers.offer(session);
        }
    }

    // 方法：从等待队列中移除用户
    private void removeFromWaitingQueue(WebSocketSession session) {
        synchronized (waitingUsers) {
            waitingUsers.remove(session);
        }
    }

    public void addSession(Long userId, WebSocketSession session) {
        userSessions.put(userId, session);
    }

    public void removeSession(Long userId) {
        userSessions.remove(userId);
    }

    public WebSocketSession getSession(Long userId) {
        return userSessions.get(userId);
    }

    public void userOnline(Long userId) {
        onlineUsers.add(userId);
    }

    public void userOffline(Long userId) {
        onlineUsers.remove(userId);
    }

    /*
     * WebSocket 连接建立后的处理逻辑
     * 1. 获取用户 ID
     * 2. 将用户添加到在线用户列表
     * 3. 将用户会话添加到管理中
     * 4. 将用户添加到等待匹配队列
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        Long userId = (Long) session.getAttributes().get("userId");// 从session中获取用Id
        userOnline(userId); // 用户上线
        addSession(userId, session); // 将用户会话添加到管理中
        // 将用户添加到等待匹配队列
        addToWaitingQueue(session);
    }

    /*
    函数名：handleTextMessage
    作用：
        * 处理前端发送的消息
        * 1. 如果消息内容为 "matchRequest"，则尝试匹配用户
            * 1.1. 如果没有匹配的用户，返回失败信息
            * 1.2. 如果匹配成功，向两个用户发送匹配成功消息,将匹配的两个用户从等待队列中移除
        * 2. 如果消息内容为 "msg:消息内容"，则将消息发送给匹配的用户
    参数：
        * WebSocketSession session：WebSocket 会话
        * TextMessage message：消息命令内容
     */
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        final Logger logger = LoggerFactory.getLogger(ChatWebSocketHandler.class);

        String payload = message.getPayload();
        Long userId = (Long) session.getAttributes().get("userId");
        Long matchedUserId = -1L;  // 初始值 -1 表示未匹配


        if (payload.equals("matchRequest")) {
            // 尝试从等待队列中获取一个用户进行随机匹配

            logger.info("userId:{} matching...", userId);

            WebSocketSession matchedUserSession = findMatch(session);

            if (matchedUserSession != null
                    && matchedUserSession.isOpen()
                    && matchedUserSession.getAttributes().get("userId") != userId) {

                logger.info("userId:{} match successful", userId);

                matchedUserId = (Long) matchedUserSession.getAttributes().get("userId");

                // 将匹配成功的用户Id记录在后端
                userMatches.put(userId, matchedUserId);
                userMatches.put(matchedUserId, userId);  // 反向映射，确保双向匹配

                // 向两个用户发送匹配成功消息
                Map<String, Object> responseUser = new HashMap<>();
                Map<String, Object> responseMatchedUser = new HashMap<>();

                responseUser.put("success", true);
                sendResponse(session, responseUser);

                responseMatchedUser.put("success", true);
                sendResponse(matchedUserSession, responseMatchedUser);

                // 将匹配的两个用户从等待队列中移除
                removeFromWaitingQueue(session);
                removeFromWaitingQueue(matchedUserSession);

            } else {
                // 没有匹配的用户，返回失败信息
                logger.info("userId:{} match failed", userId);
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                sendResponse(session, response);  // 向当前用户发送提示消息
            }
            return;
        }
        logger.info("Enter message processing");
        // 消息格式为 "msg:消息内容"
        String[] parts = payload.split(":", 2); // 分割消息内容
        if (parts.length == 2) {
            String messageContent = parts[1];
            logger.info("MessageInfo: {}", messageContent);
            // 获取目标用户的 userId
            if (userMatches.containsKey(userId)) {
                Long targetUserId = userMatches.get(userId);
                WebSocketSession targetSession = getSession(targetUserId);
                if (targetSession != null && targetSession.isOpen()) {
                    targetSession.sendMessage(new TextMessage("msg:" + messageContent));
                    logger.info("message from {} to {}", userId, targetUserId);
                } else {
                    Map<String, Object> responseUser = new HashMap<>();
                    responseUser.put("success", false);
                    sendResponse(session, responseUser);
                }
            }
        }
    }

    /*
    匹配逻辑
    1. 从等待队列中取出第一个用户
    2. 遍历等待队列，寻找有效的用户进行匹配
     */
    private WebSocketSession findMatch(WebSocketSession session) {
        WebSocketSession matchedUserSession = null;
        synchronized (waitingUsers) {
            // 如果等待队列为空，返回null
            if (waitingUsers.size() < 2) {
                return null;
            }
            // 遍历等待队列，寻找有效的用户进行匹配
            while (!waitingUsers.isEmpty()) {
                matchedUserSession = waitingUsers.poll();  // 从队列中取出第一个用户
                if (matchedUserSession != null && matchedUserSession.isOpen() && matchedUserSession != session) {
                    break;  // 找到匹配用户，退出循环
                }
            }
        }
        return matchedUserSession;
    }

    /*
    函数名：sendResponse
    作用：WebSocket此处仅能处理字符串消息，此处将 Map 转换为 JSON 字符串后发送给前端，由前端执行 JSON.parse 解析
    参数：
        * WebSocketSession session：WebSocket 会话
        * Map<String, Object> responseData：响应数据
     */
    private void sendResponse(WebSocketSession session,
                              Map<String, Object> responseData) throws Exception {
        // 将 Map 转换为 JSON 字符串
        String jsonResponse = jacksonObjectMapper.writeValueAsString(responseData);
        // 发送给前端
        session.sendMessage(new TextMessage(jsonResponse));
    }

/**
 * 处理WebSocket传输错误。
 *
 * @param session 当前的WebSocket会话
 * @param exception 发生的异常
 * @throws Exception 可能抛出的异常
 */
    @Override
    public void handleTransportError(WebSocketSession session,
                                     Throwable exception) throws Exception {

        // 创建日志记录器，用于记录错误信息
        final Logger logger = LoggerFactory.getLogger(ChatWebSocketHandler.class);

        // 使用try-catch结构来处理潜在的异常
        try {
            // 记录传输错误
            logger.error("Transport error", exception);
        } catch (Exception e) {
            // 如果在记录错误时发生异常，记录该异常信息
            logger.error(e.getMessage(), e);
        }
    }


/**
 * 处理接收到的二进制消息。
 *
 * @param session 当前的WebSocket会话
 * @param message 接收到的二进制消息
 */
    @Override
    public void handleBinaryMessage(WebSocketSession session,
                                    BinaryMessage message) {

        // 创建日志记录器，用于记录信息
        final Logger logger = LoggerFactory.getLogger(ChatWebSocketHandler.class);

        // 使用try-catch结构来处理潜在的异常
        try {
            // 调用父类的方法处理二进制消息
            super.handleBinaryMessage(session, message);
        } catch (Exception e) {
            // 如果处理二进制消息时发生异常，记录该异常信息
            logger.info("WebSocket handleBinaryMessage异常", e);
        }
    }


/**
 * 处理接收到的Pong消息。
 *
 * @param session 当前的WebSocket会话
 * @param pongMessage 接收到的Pong消息
 */
    @Override
    public void handlePongMessage(WebSocketSession session,
                                  PongMessage pongMessage) {

        // 创建日志记录器，用于记录信息
        final Logger logger = LoggerFactory.getLogger(ChatWebSocketHandler.class);

        // 使用try-catch结构来处理潜在的异常
        try {
            // 调用父类的方法处理 Pong 消息
            super.handlePongMessage(session, pongMessage);
        } catch (Exception e) {
            // 如果处理 Pong 消息时发生异常，记录该异常信息
            logger.info("WebSocket handlePongMessage异常:", e);
        }
    }


/**
 * 在WebSocket连接关闭后执行的操作。
 *
 * @param session 当前的WebSocket会话
 * @param status 关闭状态
 * @throws Exception 可能抛出的异常
 */
    @Override
    public void afterConnectionClosed(WebSocketSession session,
                                      CloseStatus status) throws Exception {

        // 从会话属性中获取用户ID
        Long userId = (Long) session.getAttributes().get("userId");

        // 用户下线，执行相关处理
        userOffline(userId);  // 处理用户下线逻辑

        // 移除用户会话，释放相关资源
        removeSession(userId); // 移除用户的会话信息

        // 用户下线时，从等待队列中移除
        removeFromWaitingQueue(session); // 从等待队列中移除该用户

        // 如果用户退出，清除匹配关系
        userMatches.remove(userId); // 移除用户的匹配关系
    }
}