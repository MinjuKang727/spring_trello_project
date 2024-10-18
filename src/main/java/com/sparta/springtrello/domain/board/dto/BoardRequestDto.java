package com.sparta.springtrello.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardRequestDto {
    private  String title;
    private  String backgroundcolor;
    private  String backgroundimage;

}