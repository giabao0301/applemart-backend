package com.applemart.notification.kafka;

import com.applemart.notification.NotificationRequest;
import com.applemart.notification.NotificationService;
import com.applemart.notification.email.EmailDetails;
import com.applemart.notification.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.applemart.notification.email.EmailBuilder.buildEmail;

@Component
@RequiredArgsConstructor
public class NotificationListener {

    private static final Logger log = LoggerFactory.getLogger(NotificationListener.class);
    private final EmailService emailService;
    private final NotificationService notificationService;

    @KafkaListener(topics = "notification", groupId = "notification-group")
    public void listener(NotificationRequest data) {
        log.info("Received notification: {}", data);
        
        EmailDetails email = EmailDetails.builder()
                .recipient(data.getToUserEmail())
                .subject(data.getSubject())
                .msgBody(buildEmail(data))
                .build();

        emailService.send(email);

        notificationService.saveNotification(data);
    }
}
