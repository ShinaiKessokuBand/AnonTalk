package org.shinaikessokuband.anontalk.chat;

import org.shinaikessokuband.anontalk.service.UserService;
import org.shinaikessokuband.anontalk.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private UserService userService;
    @Autowired
    private MatchService matchService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String username = (String) session.getAttributes().get("username"); // 从session中获取用户名
        userService.userOnline(username); // 用户上线
        userService.addSession(username, session); // 将用户会话添加到管理中
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        String username = (String) session.getAttributes().get("username");

        if (payload.equals("findMatch")) {
            String matchedUser = matchService.findMatch(username);
            if (matchedUser != null) {
                session.sendMessage(new TextMessage("Matched with: " + matchedUser));
            } else {
                session.sendMessage(new TextMessage("No one is available for matching"));
            }
        } else if (payload.startsWith("msg:")) {
            // 消息格式为 "msg:目标用户名:消息内容"
            String[] parts = payload.split(":", 3); // 分割消息内容
            if (parts.length == 3) {
                String targetUser = parts[1];
                String messageContent = parts[2];

                // 获取目标用户的会话
                WebSocketSession targetSession = userService.getSession(targetUser);
                if (targetSession != null && targetSession.isOpen()) {
                    targetSession.sendMessage(new TextMessage(username + ": " + messageContent));
                } else {
                    session.sendMessage(new TextMessage("User " + targetUser + " is not online."));
                }
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String username = (String) session.getAttributes().get("username");
        userService.userOffline(username); // 用户下线
        userService.removeSession(username); // 移除用户会话
    }
}

