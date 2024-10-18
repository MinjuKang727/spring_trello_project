package com.sparta.springtrello.domain.auth.controller;

import com.sparta.springtrello.common.ApiResponse;
import com.sparta.springtrello.domain.auth.service.KakaoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KakaoController {

    private final KakaoService kakaoService;

    // 카카오 로그인 요청
    @GetMapping("/auth/kakao")
    public String kakaoConnect() {
        return kakaoService.kakaoLogin();
    }

    @GetMapping("/auth/kakaoCallback")
    public ResponseEntity<ApiResponse<String>> kakaoSignin(HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(ApiResponse.onSuccess(kakaoService.callback(request.getParameter("code"))));
    }
}
