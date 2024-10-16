package com.sparta.springtrello.domain.deck.dto.response;

import com.sparta.springtrello.domain.deck.entity.Deck;
import lombok.Getter;

@Getter
public class DeckResponse {
    private final Long id;
    private final String name;
    private final int order;

    public DeckResponse(Deck deck) {
        this.id = deck.getId();
        this.name = deck.getName();
        this.order = deck.getOrder();
    }
}
