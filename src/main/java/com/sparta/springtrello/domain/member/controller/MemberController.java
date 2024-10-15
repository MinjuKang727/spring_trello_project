package com.sparta.springtrello.domain.member.controller;


import com.sparta.springtrello.domain.member.dto.MemberRequestDto;
import com.sparta.springtrello.domain.member.dto.MemberResponseDto;
import com.sparta.springtrello.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    // 멤버 초대
    @PostMapping("/workspaces/{workspaceId}/invitations/users/{userId}")
    public ResponseEntity<MemberResponseDto> inviteUser(
//            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long workspaceId,
            @PathVariable Long userId
    ) {
        MemberResponseDto response = memberService.inviteUser(workspaceId);
        return ResponseEntity.ok(response);
    }

    // 초대 수락
    @PutMapping("/workspaces/invitations/{memberId}")
    public ResponseEntity<MemberResponseDto> acceptWorkspace(
            @PathVariable Long memberId
    ) {
        MemberResponseDto response = memberService.acceptWorkspace(memberId);
        return ResponseEntity.ok(response);
    }

    // 멤버 권한 변경
    @PutMapping("/members/{memberId}")
    public ResponseEntity<MemberResponseDto> changeRole(
//            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long memberId,
            @RequestBody MemberRequestDto requestDto
    ) {
        MemberResponseDto response = memberService.changeRole(memberId, requestDto);
        return ResponseEntity.ok(response);
    }


}
