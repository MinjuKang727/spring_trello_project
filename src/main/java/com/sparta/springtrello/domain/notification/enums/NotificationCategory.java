package com.sparta.springtrello.domain.notification.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum NotificationCategory {
    WORKSPACE(Category.WORKSPACE),
    BOARD(Category.BOARD),
    DECK(Category.DECK),
    CARD(Category.CARD),
    MEMEBER(Category.MEMBER);

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
        public static final String BOARD = "CATEGORY_BOARD";
        public static final String DECK = "CATEGORY_DECK";
        public static final String MEMBER = "CATEGORY_MEMBER";

    }
}