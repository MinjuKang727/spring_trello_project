package com.sparta.springtrello.domain.comment.dto;

import lombok.Getter;


@Getter
public class CommentResponseDto {
    private final Long cardid;
    private final String cardTitle;
    private final Long authorId;
    private final String contents;


    public CommentResponseDto(Long cardid, String cardTitle, Long authorId, Long id, String contents) {
        this.cardid = cardid;
        this.cardTitle = cardTitle;
        this.authorId = authorId;
        this.contents = contents;
    }
}