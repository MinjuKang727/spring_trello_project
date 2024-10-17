package com.sparta.springtrello.domain.manager.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ManagerResponseDto {
    private final Long managerId;
    private final Long userId;
    private final String userNickname;
}
