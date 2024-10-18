package com.sparta.springtrello.domain.card.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CardSearchResponseDto {
    private final Long cardId;
    private final String cardTitle;
    private final Long managerCount;
}
