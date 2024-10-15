package com.sparta.springtrello.common.domain.notification.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum NotificationCategory {
    WORKSPACE(Category.WORKSPACE),
    CARD(Category.CARD);

    private final String category;

    public static NotificationCategory of(String category) {
        return Arrays.stream(NotificationCategory.values())
                .filter(notificationCategory -> notificationCategory.name().equalsIgnoreCase(category))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 알람 분류입니다."));
    }

    public static class Category {
        public static final String WORKSPACE = "CATEGORY_WORKSPACE";
        public static final String CARD = "CATEGORY_CARD";
    }
}