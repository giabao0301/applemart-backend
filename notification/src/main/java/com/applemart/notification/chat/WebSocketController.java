package com.applemart.notification.chat;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Date;

@Controller
public class WebSocketController {

    private static final Logger log = LoggerFactory.getLogger(WebSocketController.class);
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private MessageRepository messageRepository;

    @MessageMapping("/chat")
    public void sendPrivateMessage(@Payload Message message, Principal principal) {
        Chat chat = chatRepository.findByUser1IdAndUser2Id(message.getSenderId(), message.getReceiverId())
                .orElseGet(() -> {
                    Chat newChat = new Chat();
                    newChat.setUser1Id(message.getSenderId());
                    newChat.setUser2Id(message.getReceiverId());
                    return chatRepository.save(newChat);
                });

        message.setChatRoomId(chat.getId());
        message.setTimestamp(new Date());

        Message savedMessage = messageRepository.save(message);

        log.info("Principal: {}", principal.getName());

        messagingTemplate.convertAndSendToUser(
                String.valueOf(message.getReceiverId()), "/queue/messages",
                savedMessage
        );
    }
}
