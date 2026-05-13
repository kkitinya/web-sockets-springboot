package com.curro.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.JacksonJsonMessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    /**
     * Register STOMP endpoints mapping each to a specific URL and (optionally)
     * enabling and configuring SockJS fallback options.
     *
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
            .addEndpoint("/ws")
//            .setAllowedOrigins("*") // Allow all origins for CORS
            .withSockJS();
    }

    /**
     * Configure message broker options.
     *
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/user","/topic");
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/user");
    }

    /**
     * Configure the message converters to use when extracting the payload of
     * messages in annotated methods and when sending messages (for example, through the
     * "broker" SimpMessagingTemplate).
     * <p>The provided list, initially empty, can be used to add message converters
     * while the boolean return value is used to determine if default message should
     * be added as well.
     *
     * @param messageConverters the converters to configure (initially an empty list)
     * @return whether to also add default converter or not
     */
    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        messageConverters.add( new JacksonJsonMessageConverter());
        return false;
    }
}
