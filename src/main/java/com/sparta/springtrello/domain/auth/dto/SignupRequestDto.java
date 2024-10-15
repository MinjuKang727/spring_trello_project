package com.sparta.springtrello.domain.auth.dto;

import lombok.Getter;

@Getter
public class SignupRequestDto {
    private String email;
    private String password;
    private String nickname;
    private String adminKey;
    //private int authNumber;
}
