package com.sparta.springtrello.domain.member.service;

import com.sparta.springtrello.common.ErrorStatus;
import com.sparta.springtrello.common.exception.ApiException;
import com.sparta.springtrello.domain.member.entity.Member;
import com.sparta.springtrello.domain.member.enums.InvitationStatus;
import com.sparta.springtrello.domain.member.enums.MemberRole;
import com.sparta.springtrello.domain.member.repository.MemberRepository;
import com.sparta.springtrello.domain.user.entity.User;
import com.sparta.springtrello.domain.workspace.entity.Workspace;
import com.sparta.springtrello.domain.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberAuthorizeService {
    private final MemberRepository memberRepository;
    private final WorkspaceRepository workspaceRepository;

    // 특정 workspaceId에 대한 Member 객체를 Optional로 반환하는 공통 메서드
    private Optional<Member> findMemberByWorkspaceId(User user, Long workspaceId) {
        Workspace workspace = workspaceRepository.findById(workspaceId).orElseThrow(
                ()-> new ApiException(ErrorStatus.NOT_FOUND_WORKSPACE)
        );
        return memberRepository.findAcceptedMember(user,workspace, InvitationStatus.ACCEPT);
    }

    // workspace에 접근할 수 있는지 검증
    public boolean hasAccessToWorkspace(User user, Long workspaceId) {
        // 공통 메서드를 이용하여 Member 존재 여부를 확인
        return findMemberByWorkspaceId(user, workspaceId).isPresent();
    }

    // 워크스페이스에 대한 역할이 READ_ONLY인지 검증
    public boolean hasReadOnlyRole(User user, Long workspaceId) {
        return findMemberByWorkspaceId(user, workspaceId)
                .map(member -> member.getMemberRole() != MemberRole.READ_ONLY)
                .orElse(false);
    }

}
