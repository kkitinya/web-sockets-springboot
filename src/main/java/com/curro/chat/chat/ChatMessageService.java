package com.curro.chat.chat;

import com.curro.chat.chatroom.ChatRoomService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository repository;
    private final ChatRoomService  chatRoomService;

    public ChatMessage save(ChatMessage chatMessage) {
        String chatRoomId = chatRoomService.getChatRoomId(
                chatMessage.getSenderId(),
                chatMessage.getRecipientId(),
                true
        ).orElseThrow();

        chatMessage.setChatId(chatRoomId);
        return repository.save(chatMessage);
    }

    public List<ChatMessage> findChatMessages(String senderId, String recipientId) {
        return  chatRoomService.getChatRoomId(senderId, recipientId, true)
                .map(repository::findAllByChatId)
                .orElse(new ArrayList<>());
    }


}
