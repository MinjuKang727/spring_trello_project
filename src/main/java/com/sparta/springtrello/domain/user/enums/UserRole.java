package com.sparta.springtrello.domain.user.enums;

import com.sparta.springtrello.common.exception.InvalidRequestException;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum UserRole {

    ROLE_ADMIN(Authority.ADMIN),
    ROLE_USER(Authority.USER);

    private final String userRole;

    public static UserRole of(String role) {
        return Arrays.stream(UserRole.values())
                .filter(r -> r.name().equalsIgnoreCase(role))
                .findFirst()
                .orElseThrow(() -> new InvalidRequestException("유효하지 않은 권한입니다."));
    }

    public static class Authority {
        public static final String ADMIN = "ROLE_ADMIN";
        public static final String USER = "ROLE_USER";
    }
}
