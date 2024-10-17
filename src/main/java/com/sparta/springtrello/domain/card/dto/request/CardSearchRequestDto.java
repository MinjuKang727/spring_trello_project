package com.sparta.springtrello.domain.card.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CardSearchRequestDto {
    @Min(value = 1, message = "페이지 번호는 1 이상이어야 합니다.")
    private int page = 1;
    @Min(value = 1, message = "페이지 크기는 최소 1이어야 합니다.")
    private int size = 10;
    @Size(max = 255, message = "제목은 최대 255자까지 입력할 수 있습니다.")
    private String title;
    @Size(max = 20, message = "매니저 닉네임은 최대 20자까지 입력할 수 있습니다.")
    private String managerNickname;
    @FutureOrPresent(message = "마감일은 과거일 수 없습니다.")
    private Date deadline;
    private Long boardId;
}
