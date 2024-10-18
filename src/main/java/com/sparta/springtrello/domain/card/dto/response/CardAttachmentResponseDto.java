package com.sparta.springtrello.domain.card.dto.response;

import lombok.Getter;

@Getter
public class CardAttachmentResponseDto {
    private final Long cardId;
    private final String title;
    private final String fileUrl;

    public CardAttachmentResponseDto(Long cardId,String title, String fileUrl) {
        this.cardId = cardId;
        this.title = title;
        this.fileUrl = fileUrl;
    }
}
