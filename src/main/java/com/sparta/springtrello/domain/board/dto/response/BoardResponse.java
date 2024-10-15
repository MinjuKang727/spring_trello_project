package com.sparta.springtrello.domain.board.dto.response;

import com.sparta.springtrello.domain.board.entity.Board;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BoardResponse {
    private final Long boardId;
    private final String boardName;

    public BoardResponse(Board board) {
        this.boardId = board.getId();
        this.boardName = board.getName();
    }
}
