package com.sparta.springtrello.domain.user.controller;

import com.sparta.springtrello.common.ApiResponse;
import com.sparta.springtrello.domain.auth.dto.AuthUser;
import com.sparta.springtrello.domain.user.dto.UserDeleteRequestDto;
import com.sparta.springtrello.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PatchMapping("/user/delete")
    public ResponseEntity<ApiResponse<String>> deleteUser(
            HttpServletRequest httpServletRequest,
            @AuthenticationPrincipal AuthUser authUser,
            @RequestBody UserDeleteRequestDto requestDto) {
        return ResponseEntity.ok(ApiResponse.onSuccess(userService.deleteUser(httpServletRequest, authUser, requestDto)));
    }

    @DeleteMapping("/user/logout")
    public ResponseEntity<ApiResponse<String>> logout(HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(ApiResponse.onSuccess(userService.logout(httpServletRequest)));
    }

}
