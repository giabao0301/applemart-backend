package com.applemart.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{

    private final NotificationRepository notificationRepository;

    @Override
    public void saveNotification(NotificationRequest request) {
        Notification notification = Notification.builder()
                .toUserName(request.getToUserName())
                .toUserEmail(request.getToUserEmail())
                .sender("Applemart")
                .message(request.getMessage())
                .sentAt(LocalDateTime.now())
                .build();

        notificationRepository.save(notification);
    }
}
