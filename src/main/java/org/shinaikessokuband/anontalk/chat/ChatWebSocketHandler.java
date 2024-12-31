package org.shinaikessokuband.anontalk.chat;

import lombok.Getter;
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
    private final Map<String, WebSocketSession> userSessions = Collections.synchronizedMap(new HashMap<>());
    @Getter
    private final Set<String> onlineUsers = Collections.synchronizedSet(new HashSet<String>());

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

    public void addSession(String username, WebSocketSession session) {
        userSessions.put(username, session);
    }

    public void removeSession(String username) {
        userSessions.remove(username);
    }

    public WebSocketSession getSession(String username) {
        return userSessions.get(username);
    }

    public void userOnline(String username) {
        onlineUsers.add(username);
    }

    public void userOffline(String username) {
        onlineUsers.remove(username);
    }

    @Autowired
    private UserService userService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        String username = (String) session.getAttributes().get("username"); // 从session中获取用户名
        userOnline(username); // 用户上线
        addSession(username, session); // 将用户会话添加到管理中

        // 将用户添加到等待匹配队列
        addToWaitingQueue(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        String username = (String) session.getAttributes().get("username");

        if (payload.equals("findMatch")) {
            // 尝试从等待队列中获取一个用户进行随机匹配
            WebSocketSession matchedUserSession = findMatch(session);
            if (matchedUserSession != null) {
                // 匹配成功，向两个用户发送消息
                String matchedUser = (String) matchedUserSession.getAttributes().get("username");
                session.sendMessage(new TextMessage("Matched with: " + matchedUser));
                matchedUserSession.sendMessage(new TextMessage("Matched with: " + username));

                // 将匹配的两个用户从等待队列中移除
                removeFromWaitingQueue(session);
                removeFromWaitingQueue(matchedUserSession);
            } else {
                // 如果没有匹配的用户，返回提示消息
                session.sendMessage(new TextMessage("No one is available for matching, please wait..."));
            }
        } else if (payload.startsWith("msg:")) {
            // 消息格式为 "msg:目标用户名:消息内容"
            String[] parts = payload.split(":", 3); // 分割消息内容
            if (parts.length == 3) {
                String targetUser = parts[1];
                String messageContent = parts[2];

                // 获取目标用户的会话
                WebSocketSession targetSession = getSession(targetUser);
                if (targetSession != null && targetSession.isOpen()) {
                    targetSession.sendMessage(new TextMessage(username + ": " + messageContent));
                } else {
                    session.sendMessage(new TextMessage("User " + targetUser + " is not online."));
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
        String username = (String) session.getAttributes().get("username");
        userOffline(username); // 用户下线
        removeSession(username); // 移除用户会话

        // 用户下线时，从等待队列中移除
        removeFromWaitingQueue(session);
    }
}
