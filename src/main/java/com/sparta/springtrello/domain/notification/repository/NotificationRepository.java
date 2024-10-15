package com.sparta.springtrello.domain.notification.repository;

import com.sparta.springtrello.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long>, NotificationQueryDslRepository {
}
