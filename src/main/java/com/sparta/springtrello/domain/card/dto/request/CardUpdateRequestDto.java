package com.sparta.springtrello.domain.card.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CardUpdateRequestDto {
    private String title;
    private String contents;
    private Date deadline;
}
