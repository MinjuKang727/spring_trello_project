package com.sparta.springtrello.common.domain.notification.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SlackMessageResponse {
    private final String test;  // Slack에 표시될 메시지
}
