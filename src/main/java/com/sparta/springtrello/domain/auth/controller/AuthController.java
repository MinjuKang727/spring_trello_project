package com.sparta.springtrello.domain.auth.controller;

import com.sparta.springtrello.domain.auth.dto.SigninRequestDto;
import com.sparta.springtrello.domain.auth.dto.SigninResponseDto;
import com.sparta.springtrello.domain.auth.dto.SignupRequestDto;
import com.sparta.springtrello.domain.auth.dto.SignupResponseDto;
import com.sparta.springtrello.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody SignupRequestDto request) {
        return ResponseEntity.ok(authService.signup(request));
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<SigninResponseDto> signin(@RequestBody SigninRequestDto request) {
        return ResponseEntity.ok(authService.signin(request));
    }
}
