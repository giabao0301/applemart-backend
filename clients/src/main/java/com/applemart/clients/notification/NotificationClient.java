package com.applemart.clients.notification;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "notification", url = "http://localhost:8082")
public interface NotificationClient {

    @PostMapping("/api/v1/notification")
    void publishNotification(NotificationRequest notificationRequest);
}
