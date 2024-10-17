package com.sparta.springtrello.domain.notification.dto.response;

import com.sparta.springtrello.domain.member.dto.MemberResponseDto;
import com.sparta.springtrello.domain.user.enums.UserRole;

public class SlackUserResponse {

    private Long userId;
    private String email;
    private String nickname;
    private UserRole userRole;
    private String slackChannelId;
    private MemberResponseDto member;



}
