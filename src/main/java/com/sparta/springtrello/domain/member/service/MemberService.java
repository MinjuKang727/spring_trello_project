package com.sparta.springtrello.domain.member.service;


import com.sparta.springtrello.domain.member.dto.MemberRequestDto;
import com.sparta.springtrello.domain.member.dto.MemberResponseDto;
import com.sparta.springtrello.domain.member.entity.Member;
import com.sparta.springtrello.domain.member.enums.MemberRole;
import com.sparta.springtrello.domain.member.repository.MemberRepository;
import com.sparta.springtrello.domain.workspace.entity.Workspace;
import com.sparta.springtrello.domain.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final WorkspaceRepository workspaceRepository;

    public void inviteUser(Long workspaceId) {

    }

    public MemberResponseDto acceptWorkspace(Long workspaceId) {
        Workspace workspace = workspaceRepository.findById(workspaceId).orElseThrow(() ->
                new NullPointerException("워크스페이스가 존재하지 않습니다."));
        Member member = new Member(
                workspace,
                MemberRole.READ_ONLY
        );
        memberRepository.save(member);
        return new MemberResponseDto(
                member.getWorkspace().getId(),
                member.getMemberRole()
        );
    }

    @Transactional
    public MemberResponseDto changeRole(Long memberId, MemberRequestDto requestDto) {
        // 검증

        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new NullPointerException("멤버가 존재하지 않습니다."));
        member.updateRole(requestDto.getMemberRole());

        return new MemberResponseDto(
                member.getWorkspace().getId(),
                member.getMemberRole()
        );
    }
}
