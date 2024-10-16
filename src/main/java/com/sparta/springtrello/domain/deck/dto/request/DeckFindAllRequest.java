package com.sparta.springtrello.domain.deck.dto.request;

import lombok.Getter;

@Getter
public class DeckFindAllRequest {
    private final Long boardId;
    private final Integer page;
    private final Integer size;

    public DeckFindAllRequest(Long boardId, Integer page, Integer size) {
        this.boardId = boardId;
        this.page = page == null ? 1 : page;
        this.size = size == null ? 10 : size;
    }
}
