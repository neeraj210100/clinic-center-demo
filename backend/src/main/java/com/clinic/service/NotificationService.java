package com.clinic.service;

import com.clinic.model.Appointment;
import com.clinic.model.Notification;
import com.clinic.notification.NotificationException;
import com.clinic.notification.NotificationSender;
import com.clinic.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);
    private static final String REFERENCE_TYPE_APPOINTMENT = "APPOINTMENT";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("EEEE, MMM d, yyyy 'at' h:mm a");

    private final NotificationRepository notificationRepository;
    private final TemplateEngine templateEngine;
    private final Map<Notification.NotificationType, NotificationSender> senders;
    private final NotificationService self;

    @Value("${notification.email.clinic-to:appointments@clinic.com}")
    private String clinicToEmail;

    public NotificationService(NotificationRepository notificationRepository,
                               TemplateEngine templateEngine,
                               List<NotificationSender> senderList,
                               @Lazy NotificationService self) {
        this.notificationRepository = notificationRepository;
        this.templateEngine = templateEngine;
        this.senders = senderList.stream()
                .collect(Collectors.toMap(NotificationSender::getType, Function.identity()));
        this.self = self;
    }

    public void sendAppointmentConfirmation(Appointment appointment) {
        String patientName = appointment.getPatientName();
        String formattedDateTime = appointment.getAppointmentDateTime().format(DATE_TIME_FORMATTER);
        String reason = appointment.getReason() != null ? appointment.getReason() : "Not specified";
        String clinicName = "Clinic Center";
        String clinicPhone = "+1 (234) 567-8900";

        Context context = new Context();
        context.setVariable("patientName", patientName);
        context.setVariable("appointmentDateTime", formattedDateTime);
        context.setVariable("reason", reason);
        context.setVariable("clinicName", clinicName);
        context.setVariable("clinicPhone", clinicPhone);

        // 1. Client (patient) notification
        String emailContent = templateEngine.process("appointment-confirmation-email", context);
        Notification clientNotification = new Notification();
        clientNotification.setType(Notification.NotificationType.EMAIL);
        clientNotification.setRecipient(appointment.getEmail());
        clientNotification.setSubject("Appointment Confirmation – " + clinicName);
        clientNotification.setContent(emailContent);
        clientNotification.setStatus(Notification.NotificationStatus.PENDING);
        clientNotification.setReferenceType(REFERENCE_TYPE_APPOINTMENT);
        clientNotification.setReferenceId(appointment.getId());
        clientNotification = notificationRepository.save(clientNotification);
        self.sendWithRetry(clientNotification);

        // 2. Server (clinic) notification – copy to clinic email
        context.setVariable("patientEmail", appointment.getEmail());
        context.setVariable("patientPhone", appointment.getPhoneNumber());
        context.setVariable("notes", appointment.getNotes() != null ? appointment.getNotes() : "");
        String clinicEmailContent = templateEngine.process("appointment-notification-clinic", context);
        Notification clinicNotification = new Notification();
        clinicNotification.setType(Notification.NotificationType.EMAIL);
        clinicNotification.setRecipient(clinicToEmail);
        clinicNotification.setSubject("New appointment: " + patientName + " – " + formattedDateTime);
        clinicNotification.setContent(clinicEmailContent);
        clinicNotification.setStatus(Notification.NotificationStatus.PENDING);
        clinicNotification.setReferenceType(REFERENCE_TYPE_APPOINTMENT);
        clinicNotification.setReferenceId(appointment.getId());
        clinicNotification = notificationRepository.save(clinicNotification);
        self.sendWithRetry(clinicNotification);
    }

    @Async
    @Retryable(
            value = NotificationException.class,
            maxAttempts = 1,
            backoff = @Backoff(delay = 1000, multiplier = 2)
    )
    public void sendWithRetry(Notification notification) {
        NotificationSender sender = senders.get(notification.getType());
        if (sender == null) {
            throw new IllegalArgumentException("No sender for type: " + notification.getType());
        }
        try {
            sender.send(notification);
            notification.setStatus(Notification.NotificationStatus.SENT);
            notification.setSentAt(java.time.LocalDateTime.now());
            notificationRepository.save(notification);
            log.info("Notification sent successfully: id={}, type={}", notification.getId(), notification.getType());
        } catch (NotificationException e) {
            notification.setRetryCount(notification.getRetryCount() + 1);
            notification.setStatus(Notification.NotificationStatus.RETRY);
            notificationRepository.save(notification);
            log.warn("Notification failed, will retry: id={}, attempt={}", notification.getId(), notification.getRetryCount(), e);
            throw e;
        }
    }

    @Recover
    public void recoverFromFailure(NotificationException e, Notification notification) {
        log.error("Notification permanently failed after retries: id={}", notification.getId(), e);
        notification.setStatus(Notification.NotificationStatus.FAILED);
        notificationRepository.save(notification);
    }

    public Optional<Notification> findById(Long id) {
        return notificationRepository.findById(id);
    }
}
