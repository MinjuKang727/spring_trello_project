package com.sparta.springtrello.domain.list.dto.response;

import com.sparta.springtrello.domain.board.dto.response.BoardResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DeckCreateResponse {
    private final BoardResponse board;
    private final Long listId;
    private final String listName;
    private final int listOrder;
}
