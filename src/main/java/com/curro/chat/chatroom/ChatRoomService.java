package com.curro.chat.chatroom;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository repository;

    public Optional<String> getChatRoomId(
            String senderId,
            String recipientId,
            boolean createNewRoomIfNotExists
    ){
        return repository.findBySenderIdAndRecipientId(senderId, recipientId)
                .map(ChatRoom::getChatId)
                .or(()->{
                    if (createNewRoomIfNotExists) {
                        String chatId = createChatRoom(senderId, recipientId);
                        return Optional.of(chatId);
                    }
                    return Optional.empty();

                        }

                );
    }

    private String createChatRoom(String senderId, String recipientId) {
        String chatId = String.format("%s_%s", senderId, recipientId);
        ChatRoom senderChatRoom = ChatRoom.builder()
                .senderId(senderId)
                .recipientId(recipientId)
                .chatId(chatId)
                .build();

        ChatRoom recipientChatRoom= ChatRoom.builder()
                .chatId(chatId)
                .senderId(recipientId)
                .recipientId(senderId)
                .build();

        repository.save(senderChatRoom);
        repository.save(recipientChatRoom);
        return chatId;
    }
}
