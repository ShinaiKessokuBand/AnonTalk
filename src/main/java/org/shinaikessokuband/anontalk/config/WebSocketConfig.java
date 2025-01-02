package org.shinaikessokuband.anontalk.config;

import org.shinaikessokuband.anontalk.chat.ChatWebSocketHandler;
import org.shinaikessokuband.anontalk.chat.WebSocketHandshakeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    WebSocketHandler webSocketHandler;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new ChatWebSocketHandler(), "/ws")
                .setAllowedOrigins("*")// 设置允许的来源
                .addInterceptors(new WebSocketHandshakeInterceptor())
                .withSockJS();
    }
}
