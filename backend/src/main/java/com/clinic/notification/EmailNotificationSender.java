package com.clinic.notification;

import com.clinic.model.Notification;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailNotificationSender implements NotificationSender {

    private final JavaMailSender mailSender;

    @Value("${notification.email.from:no-reply@clinic.com}")
    private String fromAddress;

    @Value("${notification.email.enabled:true}")
    private boolean enabled;

    @Override
    public Notification.NotificationType getType() {
        return Notification.NotificationType.EMAIL;
    }

    @Override
    public void send(Notification notification) throws NotificationException {
        if (!enabled) {
            throw new NotificationException("Email notifications are disabled");
        }
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromAddress);
            helper.setTo(notification.getRecipient());
            helper.setSubject(notification.getSubject() != null ? notification.getSubject() : "Notification");
            helper.setText(notification.getContent(), true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new NotificationException("Failed to send email", e);
        }
    }
}
