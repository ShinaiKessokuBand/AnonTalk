package org.shinaikessokuband.anontalk.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
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

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        Long userId = (Long) session.getAttributes().get("userId");// 从session中获取用Id
        userOnline(userId); // 用户上线
        addSession(userId, session); // 将用户会话添加到管理中
        // 将用户添加到等待匹配队列
        addToWaitingQueue(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        final Logger logger = LoggerFactory.getLogger(ChatWebSocketHandler.class);

        String payload = message.getPayload();
        Long userId = (Long) session.getAttributes().get("userId");
        Long matchedUserId = -1L;  // 初始值 -1 表示未匹配

        logger.info("Backend Received text message from " + userId + ": " + payload);

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
                    logger.info("Prepare to send to: {}", userId);
                    WebSocketSession targetSession = getSession(targetUserId);
                    if (targetSession != null && targetSession.isOpen()) {
                        targetSession.sendMessage(new TextMessage("msg:" + messageContent));
                    } else {
                        Map<String, Object> responseUser = new HashMap<>();
                        responseUser.put("success", false);
                        sendResponse(session, responseUser);
                    }
                }
        }
    }

    // 随机匹配逻辑
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

    private void sendResponse(WebSocketSession session, Map<String, Object> responseData) throws Exception {
        // 将 Map 转换为 JSON 字符串
        String jsonResponse = jacksonObjectMapper.writeValueAsString(responseData);
        // 发送给前端
        session.sendMessage(new TextMessage(jsonResponse));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Long userId = (Long) session.getAttributes().get("userId");
        userOffline(userId);  // 用户下线
        removeSession(userId); // 移除用户会话

        // 用户下线时，从等待队列中移除
        removeFromWaitingQueue(session);

        // 如果用户退出，清除匹配关系
        userMatches.remove(userId);
    }
}