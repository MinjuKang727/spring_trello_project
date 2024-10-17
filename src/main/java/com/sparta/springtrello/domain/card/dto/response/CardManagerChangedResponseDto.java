package com.sparta.springtrello.domain.card.dto.response;

import lombok.Getter;

@Getter
public class CardManagerChangedResponseDto {
    private final Long cardId;
    private final String title;
    private final Long memberId;
    private final String nickname;

    public CardManagerChangedResponseDto(Long cardId,
                                         String title,
                                         Long memberId,
                                         String nickname) {
        this.cardId = cardId;
        this.title = title;
        this.memberId = memberId;
        this.nickname = nickname;
    }
}
