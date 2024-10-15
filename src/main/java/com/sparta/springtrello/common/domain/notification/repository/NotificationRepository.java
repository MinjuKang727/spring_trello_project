package com.sparta.springtrello.common.domain.notification.repository;

import com.sparta.springtrello.common.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long>, NotificationQueryDslRepository {
}
