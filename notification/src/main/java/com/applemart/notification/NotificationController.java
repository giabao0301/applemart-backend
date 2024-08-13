package com.applemart.notification;

import com.applemart.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/notification")
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @PostMapping
    public void publishNotification(@RequestBody NotificationRequest request) {
        kafkaTemplate.send("notification", request);
    }
}