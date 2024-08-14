package com.applemart.notification;


import com.applemart.clients.notification.NotificationRequest;

public interface NotificationService {
    void saveNotification(NotificationRequest request);
}
