package com.example.happyhousebackend.domain.notification.repository;

import com.example.happyhousebackend.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
