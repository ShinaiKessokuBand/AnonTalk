package org.shinaikessokuband.anontalk.config;

import org.shinaikessokuband.anontalk.chat.ChatWebSocketHandler;
import org.shinaikessokuband.anontalk.chat.WebSocketHandshakeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * WebSocket 配置类，负责注册 WebSocket 处理程序和拦截器。
 */
@Configuration
@EnableWebSocket // 启用 WebSocket 支持
public class WebSocketConfig implements WebSocketConfigurer {

    // 聊天 WebSocket 处理程序实例
    private final ChatWebSocketHandler chatWebSocketHandler;

    /**
     * 构造函数，通过依赖注入获取聊天 WebSocket 处理程序实例。
     *
     * @param chatWebSocketHandler 聊天 WebSocket 处理程序
     */
    @Autowired
    public WebSocketConfig(ChatWebSocketHandler chatWebSocketHandler) {
        this.chatWebSocketHandler = chatWebSocketHandler;
    }

    /**
     * 注册 WebSocket 处理程序和拦截器。
     *
     * @param registry WebSocket 处理程序注册中心
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 添加聊天 WebSocket 处理程序
        registry.addHandler(chatWebSocketHandler, "/ws") // 设置 WebSocket 处理程序的映射路径
                .setAllowedOrigins("*") // 设置允许的来源，*表示允许所有来源
                .addInterceptors(new WebSocketHandshakeInterceptor()); // 添加握手拦截器
    }
}
