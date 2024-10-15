package com.sparta.springtrello.domain.deck.dto.response;

import com.sparta.springtrello.domain.board.dto.response.BoardResponse;
import com.sparta.springtrello.domain.deck.entity.Deck;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DeckCreateResponse {
    private final BoardResponse board;
    private final Long listId;
    private final String listName;
    private final int listOrder;

    public DeckCreateResponse(Deck deck) {
        this.board = new BoardResponse(deck.getBoard());
        this.listId = deck.getId();
        this.listName = deck.getName();
        this.listOrder = deck.getOrder();
    }
}
