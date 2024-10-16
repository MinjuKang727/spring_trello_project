package com.sparta.springtrello.domain.notification.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.springtrello.common.ApiResponse;
import com.sparta.springtrello.domain.notification.service.SlackService;
import com.sparta.springtrello.domain.user.dto.UserResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class SlackController {

    private final SlackService slackService;

    /**
     * 슬랙 로그인 요청
     * @param response : HttpServletResponse 객체
     * @throws IOException
     */
    @GetMapping("/auth/login/slack")
    public void slackLogin(HttpServletResponse response) throws IOException {
        response.sendRedirect(this.slackService.slackLogin());
        response.setStatus(HttpStatus.FOUND.value());
    }

    /**
     * 슬랙 로그인 콜백 요청
     * @param code
     * @return
     * @throws JsonProcessingException
     */
    @GetMapping("/auth/login/slack/callback")
    public ResponseEntity<ApiResponse<UserResponseDto>> slackLoginCallback(@RequestParam String code) throws JsonProcessingException {
        UserResponseDto responseDto = this.slackService.slackLoginCallback(code);
        return ResponseEntity
                .status(200)
                .body(
                        ApiResponse.onSuccess(responseDto)
                );
    }


}
