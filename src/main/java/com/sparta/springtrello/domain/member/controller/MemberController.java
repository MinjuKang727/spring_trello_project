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
    @PostMapping("/workspaces/{workspaceId}/invite/users/{userId}")
    public ResponseEntity<Void> inviteUser(
//            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long workspaceId,
            @PathVariable Long userId
    ) {
        memberService.inviteUser(workspaceId);
        return ResponseEntity.ok().build();
    }

    // 초대 수락
    @PostMapping("/workspaces/{workspaceId}/accept")
    public ResponseEntity<MemberResponseDto> acceptWorkspace(
//            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long workspaceId
    ) {
        MemberResponseDto response = memberService.acceptWorkspace(workspaceId);
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
