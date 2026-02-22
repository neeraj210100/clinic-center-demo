package com.clinic.notification;

import com.clinic.model.Notification;

public interface NotificationSender {

    Notification.NotificationType getType();

    void send(Notification notification) throws NotificationException;
}
