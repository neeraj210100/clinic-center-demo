package com.clinic.repository;

import com.clinic.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByReferenceTypeAndReferenceId(String referenceType, Long referenceId);
}
