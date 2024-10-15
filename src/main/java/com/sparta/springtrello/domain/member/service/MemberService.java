package com.sparta.springtrello.domain.member.service;


import com.sparta.springtrello.common.ErrorStatus;
import com.sparta.springtrello.common.dto.AuthUser;
import com.sparta.springtrello.common.exception.ApiException;
import com.sparta.springtrello.domain.member.dto.MemberRequestDto;
import com.sparta.springtrello.domain.member.dto.MemberResponseDto;
import com.sparta.springtrello.domain.member.entity.Member;
import com.sparta.springtrello.domain.member.enums.InvitationStatus;
import com.sparta.springtrello.domain.member.enums.MemberRole;
import com.sparta.springtrello.domain.member.repository.MemberRepository;
import com.sparta.springtrello.domain.user.entity.User;
import com.sparta.springtrello.domain.user.enums.UserRole;
import com.sparta.springtrello.domain.user.repository.UserRepository;
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
    private final UserRepository userRepository;

    public MemberResponseDto inviteUser(AuthUser authUser, Long workspaceId, Long userId) {
        if (authUser.getUserRole() != UserRole.ROLE_ADMIN) {
            throw new ApiException(ErrorStatus._FORBIDDEN_TOKEN);
        }
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NullPointerException("유저가 존재하지 않습니다."));

        Workspace workspace = workspaceRepository.findById(workspaceId).orElseThrow(() ->
                new NullPointerException("워크스페이스가 존재하지 않습니다."));
        Member member = new Member(
                user,
                workspace,
                InvitationStatus.PENDING,
                MemberRole.READ_ONLY
        );
        memberRepository.save(member);
        return new MemberResponseDto(
                member.getWorkspace().getWorkspace_id(),
                member.getInvitationStatus(),
                member.getMemberRole()
        );

    }

    @Transactional
    public MemberResponseDto acceptWorkspace(Long memberId) {

        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new NullPointerException("멤버가 존재하지 않습니다."));

        member.updateInvitationStatus(InvitationStatus.ACCEPT);

        return new MemberResponseDto(
                member.getWorkspace().getWorkspace_id(),
                member.getInvitationStatus(),
                member.getMemberRole()
        );
    }

    @Transactional
    public MemberResponseDto changeRole(AuthUser authUser, Long memberId, MemberRequestDto requestDto) {
        // 검증
        if (authUser.getUserRole() != UserRole.ROLE_ADMIN) {
            throw new ApiException(ErrorStatus._FORBIDDEN_TOKEN);
        }

        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new NullPointerException("멤버가 존재하지 않습니다."));
        member.updateRole(requestDto.getMemberRole());

        return new MemberResponseDto(
                member.getWorkspace().getWorkspace_id(),
                member.getInvitationStatus(),
                member.getMemberRole()
        );
    }
}
