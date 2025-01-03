package com.applemart.notification.chat;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chats")
public class ChatController {

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping
    public List<Message> getChatHistory(@RequestParam("senderId") Integer senderId, @RequestParam("receiverId") Integer receiverId) {
        return messageRepository.findBySenderIdAndReceiverIdOrderByTimestamp(senderId, receiverId);
    }
}
