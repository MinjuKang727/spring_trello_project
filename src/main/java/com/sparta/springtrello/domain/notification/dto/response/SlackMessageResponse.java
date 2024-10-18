package com.sparta.springtrello.domain.notification.dto.response;

import com.sparta.springtrello.domain.notification.entity.Notification;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SlackMessageResponse {
    private final Long id;
    private final String message;  // Slack에 표시될 메시지
    private final LocalDateTime createdAt;

    public SlackMessageResponse(Notification notification, LocalDateTime createdAt) {
        this.id = notification.getId();
        this.message = notification.getMessage();
        this.createdAt = createdAt;
    }
}
