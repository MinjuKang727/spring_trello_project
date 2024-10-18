package com.sparta.springtrello.domain.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class KakaoDto {

    private Long id;
    private String email;

    public KakaoDto(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
