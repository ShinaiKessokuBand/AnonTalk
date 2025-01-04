package org.shinaikessokuband.anontalk.chat;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;
import java.util.Map;

public class WebSocketHandshakeInterceptor extends HttpSessionHandshakeInterceptor {


    /*
    函数名：beforeHandshake
    作用：在 WebSocket 握手之前执行，检查参数是否合法，将用户 ID 存储到 WebSocket 会话的属性中并准予连接
     */
    @Override
    public boolean beforeHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Map<String, Object> attributes) throws Exception {

        String userId = request.getURI().getQuery(); // 获取查询字符串

        if (userId != null && userId.startsWith("userId=")) {
            userId = userId.split("=")[1];  // 提取用户 ID
            try {
                // 将用户 ID 存储到 WebSocket 会话的属性中
                attributes.put("userId", Long.parseLong(userId));
            } catch (NumberFormatException e) {
                // 如果解析失败，可以抛出异常或处理无效的用户 ID
                return false;
            }
        }
        // 返回 true 表示允许 WebSocket 握手
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request,
                               ServerHttpResponse response,
                               WebSocketHandler handler,
                               Exception ex) {
        //
    }
}
