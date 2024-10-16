package com.sparta.springtrello.domain.deck.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DeckMoveRequest {
    private final Long boardId;
    private final Long deckId;
    private final Integer newOrder;
}
