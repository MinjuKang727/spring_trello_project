package com.sparta.springtrello.domain.card.dto.response;

import lombok.Getter;

@Getter
public class CardCreateResponseDto {
    private final Long cardId;
    private final String cardTitle;

    public CardCreateResponseDto(Long cardId, String title) {
        this.cardId = cardId;
        this.cardTitle = title;
    }
}
