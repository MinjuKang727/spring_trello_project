package com.sparta.springtrello.domain.notification.dto.request;

import com.sparta.springtrello.domain.notification.enums.NotificationCategory;
import com.sparta.springtrello.domain.notification.enums.NotificationMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SlackNotificationRequest {

    private final Long workspaceId;
    private final NotificationMessage message;
    private final NotificationCategory category;
    private Long contentId;

    public SlackNotificationRequest(Long workspaceId, NotificationMessage message, NotificationCategory category, Long contentId) {
        this.workspaceId = workspaceId;
        this.message = message;
        this.category = category;
        this.contentId = contentId;
    }
}
