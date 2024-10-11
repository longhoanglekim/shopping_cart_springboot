package com.trainings.shoppingcartdemo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myWebSocketHandler(), "/ws")
                //.setAllowedOrigins("http://localhost:3000", "http://192.168.31.105:3000");
                .setAllowedOrigins("*");
    }

    // Khai báo MyWebSocketHandler là một bean
    @Bean
    public MyWebSocketHandler myWebSocketHandler() {
        return new MyWebSocketHandler();
    }
}
