package com.sparta.springtrello.domain.user.dto;

import com.sparta.springtrello.domain.user.entity.User;
import com.sparta.springtrello.domain.user.enums.UserRole;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private Long userId;
    private String email;
    private String nickname;
    private UserRole userRole;

    public UserResponseDto(User user) {
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.userRole = user.getUserRole();
    }
}
