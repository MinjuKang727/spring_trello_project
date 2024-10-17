package com.sparta.springtrello.domain.card.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CardDeckMoveResponseDto {
    private final Long cardId;
    private final Long beforeDeckId;
    private final Long afterDeckId;
}
