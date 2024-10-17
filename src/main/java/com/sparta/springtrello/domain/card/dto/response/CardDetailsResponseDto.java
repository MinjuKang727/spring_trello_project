package com.sparta.springtrello.domain.card.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Getter
@NoArgsConstructor
public class CardDetailsResponseDto {
    private Long cardId;
    private String title;
    private String contents;
    private Date deadline;
    private String attachment;
    private Long managerCount;
    private Long commentCount;
}
