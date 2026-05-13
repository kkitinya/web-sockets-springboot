package com.curro.chat.chat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class SemaController {

    @MessageMapping("/pingMessage")
    @SendTo("/topic/public")
    public ChatMessage semaNami(
            @Payload  ChatMessage chatMessage) {
        log.info("Received message: {}", chatMessage);
        return chatMessage;
    }
}
