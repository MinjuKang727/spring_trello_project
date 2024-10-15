package com.sparta.springtrello.domain.list.dto.request;

import lombok.Getter;

@Getter
public class DeckFindAllRequest {
    private final Long boardId;
    private final Long page;
    private final Long size;

    public DeckFindAllRequest(Long boardId, Long page, Long size) {
        this.boardId = boardId;
        this.page = page;
        this.size = size;
    }
}
