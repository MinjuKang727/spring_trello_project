package com.sparta.springtrello.domain.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class SignupRequestDto {
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[!@#$%^&*]).{8,20}$",
            message = "비밀번호는 대소문자 포함 영문 + 숫자 + 특수문자 1개 이상 최소 8글자 입니다.")
    private String password;

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;

    private String adminKey;

    private int authNumber;
}
