package com.sparta.springtrello.domain.member.service;

import com.sparta.springtrello.common.ErrorStatus;
import com.sparta.springtrello.common.exception.ApiException;
import com.sparta.springtrello.domain.auth.dto.AuthUser;
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

    // workspace 접근할 수 있는지 검증
    public boolean hasMemberRoleWORKSPACE(AuthUser authUser, Long workspaceId) {
        // 공통 메서드를 이용하여 Member 존재 여부를 확인
        return this.memberRepository.hasMemberRoleWORKSPACE(authUser.getId(), workspaceId);
    }

    // 워크스페이스에 대한 역할이 BOARD 이상인지 검증
    public boolean hasMemberRoleOverREAD_ONLY(AuthUser authUser, Long workspaceId) {
        return this.memberRepository.hasMemberRoleOverREAD_ONLY(authUser.getId(), workspaceId);
    }

    // 워크스페이스에 대한 역할이 READ_ONLY인지 검증
    public boolean hasMemberRole(AuthUser authUser, Long workspaceId) {
        return this.memberRepository.hasMemberRole(authUser.getId(), workspaceId);
    }
}
