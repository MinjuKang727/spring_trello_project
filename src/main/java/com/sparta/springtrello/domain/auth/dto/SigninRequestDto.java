package com.sparta.springtrello.domain.auth.dto;

import lombok.Getter;

@Getter
public class SigninRequestDto {
    private String email;
    private String password;
}
