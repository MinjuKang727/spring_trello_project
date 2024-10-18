package com.sparta.springtrello.domain.deck.dto.response;

import com.sparta.springtrello.domain.board.dto.response.BoardResponse;
import com.sparta.springtrello.domain.deck.entity.Deck;
import lombok.Getter;

@Getter
public class DeckCreateResponse {
    private final BoardResponse board;
    private final Long id;
    private final String name;
    private final Integer order;

    public DeckCreateResponse(Deck deck) {
        this.board = new BoardResponse(deck.getBoard());
        this.id = deck.getId();
        this.name = deck.getName();
        this.order = deck.getOrder();
    }
}
