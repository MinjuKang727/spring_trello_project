package com.sparta.springtrello.common.domain.list.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DeckResponse {
    private final Long listId;
    private final String listName;
    private final int listOrder;
}
