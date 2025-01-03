package com.applemart.notification.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageResponse {
    private String id;
    private String senderId;
    private String recipientId;
    private String content;

    public MessageResponse(Long id, Long senderId, Long receiverId, String content) {
        this.id = id.toString();
        this.senderId = senderId.toString();
        this.recipientId = receiverId.toString();
        this.content = content;
    }
}