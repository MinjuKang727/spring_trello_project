package com.sparta.springtrello.domain.member.service;


import com.sparta.springtrello.common.ErrorStatus;
import com.sparta.springtrello.common.exception.ApiException;
import com.sparta.springtrello.domain.auth.dto.AuthUser;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final WorkspaceRepository workspaceRepository;
    private final UserRepository userRepository;

    public MemberResponseDto inviteUser(AuthUser authUser, Long workspaceId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ApiException(ErrorStatus._NOT_FOUND_USER));

        Workspace workspace = workspaceRepository.findById(workspaceId).orElseThrow(() ->
                new ApiException(ErrorStatus._NOT_FOUND_WORKSPACE));

        if (!Objects.equals(workspace.getUser().getId(), authUser.getId())) {
            throw new ApiException(ErrorStatus._FORBIDDEN_ACCESS_INVITE);
        }

        // 유저가 이미 해당 워크스페이스의 멤버인지 확인
        Optional<Member> existingMember = memberRepository.findByUserAndWorkspace(user, workspace);
        if (existingMember.isPresent()) {
            throw new ApiException(ErrorStatus._CONFLICT_MEMBER);
        }

        Member member = new Member(
                user,
                workspace,
                InvitationStatus.PENDING,
                MemberRole.READ_ONLY
        );
        memberRepository.save(member);
        return new MemberResponseDto(
                member.getWorkspace().getId(),
                member.getInvitationStatus(),
                member.getMemberRole()
        );

    }

    @Transactional
    public MemberResponseDto acceptWorkspace(Long memberId) {

        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new ApiException(ErrorStatus._NOT_FOUND_MEMBER));

        member.updateInvitationStatus(InvitationStatus.ACCEPT);

        return new MemberResponseDto(
                member.getWorkspace().getId(),
                member.getInvitationStatus(),
                member.getMemberRole()
        );
    }

    @Transactional
    public MemberResponseDto changeRole(AuthUser authUser, Long memberId, MemberRequestDto requestDto) {
        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new ApiException(ErrorStatus._NOT_FOUND_MEMBER));

        // admin 확인
        if (authUser.getUserRole() != UserRole.ROLE_ADMIN) {
            throw new ApiException(ErrorStatus._FORBIDDEN_TOKEN);
        }

        // 해당 워크스페이스의 admin인지 확인
        if (!Objects.equals(member.getWorkspace().getUser().getId(), authUser.getId())) {
            throw new ApiException(ErrorStatus._FORBIDDEN_ACCESS_CHANGE_ROLE);
        }

        member.updateRole(requestDto.getMemberRole());

        return new MemberResponseDto(
                member.getWorkspace().getId(),
                member.getInvitationStatus(),
                member.getMemberRole()
        );
    }
}
