package com.sparta.springtrello.domain.auth.controller;

import com.sparta.springtrello.common.ApiResponse;
import com.sparta.springtrello.domain.auth.dto.SigninRequestDto;
import com.sparta.springtrello.domain.auth.dto.SigninResponseDto;
import com.sparta.springtrello.domain.auth.dto.SignupRequestDto;
import com.sparta.springtrello.domain.auth.dto.SignupResponseDto;
import com.sparta.springtrello.domain.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/signup")
    public ResponseEntity<ApiResponse<SignupResponseDto>> signup(@Valid @RequestBody SignupRequestDto request) {
        return ResponseEntity.ok(ApiResponse.createSuccess("회원가입이 완료되었습니다", HttpStatus.CREATED.value(), authService.signup(request)));
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<ApiResponse<SigninResponseDto>> signin(@RequestBody SigninRequestDto request) {
        return ResponseEntity.ok(ApiResponse.onSuccess(authService.signin(request)));
    }
}
