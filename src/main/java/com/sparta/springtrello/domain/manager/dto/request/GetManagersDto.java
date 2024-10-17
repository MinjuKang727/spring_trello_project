package com.sparta.springtrello.domain.manager.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetManagersDto {
    @Min(value = 1, message = "페이지 번호는 1 이상이어야 합니다.")
    private int page = 1;
    @Min(value = 1, message = "페이지 크기는 최소 1이어야 합니다.")
    private int size = 10;
    @NotNull
    private Long cardId;
}
