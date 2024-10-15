package com.sparta.springtrello.common.domain.board.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BoardResponse {
    private final Long boardId;
    private final String boardName;
}
