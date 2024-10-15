package com.sparta.springtrello.domain.member.controller;


import com.sparta.springtrello.common.ApiResponse;
import com.sparta.springtrello.common.dto.AuthUser;
import com.sparta.springtrello.domain.member.dto.MemberRequestDto;
import com.sparta.springtrello.domain.member.dto.MemberResponseDto;
import com.sparta.springtrello.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    // 멤버 초대
    @PostMapping("/workspaces/{workspaceId}/invitations/users/{userId}")
    public ApiResponse<MemberResponseDto> inviteUser(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long workspaceId,
            @PathVariable Long userId
    ) {
        MemberResponseDto responseDto = memberService.inviteUser(authUser, workspaceId, userId);
        return ApiResponse.onSuccess(responseDto);
    }

    // 초대 수락
    @PutMapping("/workspaces/invitations/{memberId}")
    public ApiResponse<MemberResponseDto> acceptWorkspace(
            @PathVariable Long memberId
    ) {
        MemberResponseDto responseDto = memberService.acceptWorkspace(memberId);
        return ApiResponse.onSuccess(responseDto);
    }

    // 멤버 권한 변경
    @PutMapping("/members/{memberId}")
    public ApiResponse<MemberResponseDto> changeRole(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long memberId,
            @RequestBody MemberRequestDto requestDto
    ) {
        MemberResponseDto responseDto = memberService.changeRole(authUser, memberId, requestDto);
        return ApiResponse.onSuccess(responseDto);
    }


}
