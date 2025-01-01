package org.shinaikessokuband.anontalk.chat;

import lombok.Getter;
import org.shinaikessokuband.anontalk.Response;
import org.shinaikessokuband.anontalk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    @Getter
    private final Map<Long, WebSocketSession> userSessions = Collections.synchronizedMap(new HashMap<>());
    @Getter
    private final Set<Long> onlineUsers = Collections.synchronizedSet(new HashSet<>());

    // 新增：一个等待匹配的用户队列
    private final Queue<WebSocketSession> waitingUsers = new LinkedList<>();

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


    @Autowired
    private UserService userService;

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

        String payload = message.getPayload();
        Long userId = (Long) session.getAttributes().get("userId");
        Long matchedUserId = 1L;  // 假设初始匹配用户Id为 -1（表示未匹配）

        if (payload.equals("findMatch")) {
            // 尝试从等待队列中获取一个用户进行随机匹配
            WebSocketSession matchedUserSession = findMatch(session);
            if (matchedUserSession != null) {
                // 匹配成功，向两个用户发送消息
                Map<String, Object> responseUser = new HashMap<>();
                Map<String, Object> responseMatchedUser = new HashMap<>();

                matchedUserId = (Long) matchedUserSession.getAttributes().get("userId");
                responseUser.put("success", true);
                responseUser.put("matchedUserId", matchedUserId);
                session.sendMessage(new TextMessage(Response.newSuccess(responseUser.toString()).toString()));

                responseMatchedUser.put("success", true);
                responseMatchedUser.put("matchedUserId", userId);
                matchedUserSession.sendMessage(new TextMessage(Response.newSuccess(responseMatchedUser.toString()).toString()));

                // 将匹配的两个用户从等待队列中移除
                removeFromWaitingQueue(session);
                removeFromWaitingQueue(matchedUserSession);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                // 如果没有匹配的用户，返回提示消息
                session.sendMessage(new TextMessage(Response.newSuccess(response.toString()).toString()));
            }
        } else if (payload.startsWith("msg:")) {
            // 消息格式为 "msg:消息内容"
            String[] parts = payload.split(":", 2); // 分割消息内容
            if (parts.length == 2) {
                String messageContent = parts[1];
                // 获取目标用户的会话
                WebSocketSession targetSession = getSession(matchedUserId);
                if (targetSession != null && targetSession.isOpen()) {
                    Map<String, Object> responseUser = new HashMap<>();
                    responseUser.put("success", true);
                    responseUser.put("messageContent", messageContent);
                    targetSession.sendMessage(new TextMessage( Response.newSuccess(responseUser.toString()).toString()));
                } else {
                    Map<String, Object> responseUser = new HashMap<>();
                    responseUser.put("success", false);
                    session.sendMessage(new TextMessage( Response.newSuccess(responseUser.toString()).toString()));
                }
            }
        }
    }

    // 随机匹配逻辑
    private WebSocketSession findMatch(WebSocketSession session) {
        synchronized (waitingUsers) {
            // 如果等待队列为空，返回null
            if (waitingUsers.isEmpty()) {
                return null;
            }
            // 找到匹配的用户并从队列中移除
            WebSocketSession matchedUserSession = waitingUsers.poll();
            // 判断匹配用户是否有效（会话是否开启等）
            if (matchedUserSession != null && matchedUserSession.isOpen() && matchedUserSession != session) {
                return matchedUserSession;
            } else {
                // 如果匹配的用户无效，继续查找队列中的下一个用户
                return findMatch(session);
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Long userId = (Long) session.getAttributes().get("userId");
        userOffline(userId); // 用户下线
        removeSession(userId); // 移除用户会话

        // 用户下线时，从等待队列中移除
        removeFromWaitingQueue(session);
    }
}