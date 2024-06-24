package jungle.HandTris.global.config.ws;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class StompConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 메시지 브로커 설정: /topic으로 시작하는 메시지는 메시지 브로커로 전달됩니다.
        config.enableSimpleBroker("/topic", "/queue");
        // /app으로 시작하는 메시지는 메시지 핸들러로 라우팅됩니다.
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // WebSocket 엔드포인트를 등록하고 SockJS를 활성화합니다.
        registry.addEndpoint("/ws").setAllowedOrigins("http://localhost:8080", "http://localhost:3000").withSockJS();
    }
}