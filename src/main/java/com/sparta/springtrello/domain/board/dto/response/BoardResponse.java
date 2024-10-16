package com.sparta.springtrello.domain.board.dto.response;

import com.sparta.springtrello.domain.board.entity.Board;
import lombok.Getter;

@Getter
public class BoardResponse {
    private final Long id;
    private final String title;

    public BoardResponse(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
    }
}
