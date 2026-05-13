package com.curro.chat.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatMessageService  chatMessageService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    public void processMessage(
            @Payload ChatMessage chatMessage) {
        ChatMessage savedMessage = chatMessageService.save(chatMessage);
        ChatNotification notification = ChatNotification.builder()
                .senderId(savedMessage.getSenderId())
                .recipientId(savedMessage.getRecipientId())
                .content(savedMessage.getContent())
                .id(savedMessage.getId())
                .build();
        messagingTemplate.convertAndSendToUser(
                savedMessage.getSenderId(), "/queue/messages", notification
        );

    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<?> findChatMessages(
            @PathVariable String senderId,
            @PathVariable String recipientId
    ){
        return ResponseEntity.ok(chatMessageService.findChatMessages(senderId, recipientId));
    }
}
