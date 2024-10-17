package com.sparta.springtrello.domain.board.dto;

import lombok.Getter;

    @Getter
    public class BoardResponseDto {
        private final Long id;
        private final String title;
        private final Long workspaceId;
        private final String backgroundColor;
        private final String backgroundImage;
        private final boolean isDeleted;

        public BoardResponseDto(Long id, String title, Long workspaceId, String backgroundColor, String backgroundImage, boolean isDeleted) {
            this.id = id;
            this.title = title;
            this.workspaceId = workspaceId;
            this.backgroundColor = backgroundColor;
            this.backgroundImage = backgroundImage;
            this.isDeleted = isDeleted;
        }
    }
