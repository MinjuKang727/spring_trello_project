package com.sparta.springtrello.domain.card.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.sql.Date;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardUpdateResponseDto {
    private final Long cardId;
    private final String title;
    private final String contents;
    private final Date deadline;

    public CardUpdateResponseDto(Long cardId, String title, String contents, Date deadline) {
        this.cardId = cardId;
        this.title = title;
        this.contents = contents;
        this.deadline = deadline;
    }
}
