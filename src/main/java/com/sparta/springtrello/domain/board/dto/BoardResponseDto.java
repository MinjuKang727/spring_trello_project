package com.sparta.springtrello.domain.board.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BoardResponseDto {
    private final Long id;
    private final String title;
    private final Long workspaceId;
    private final String backgroundColor;
    private final String backgroundImage;
    private final boolean isDeleted;

}
