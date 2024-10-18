package com.sparta.springtrello.domain.member.repository;

import com.sparta.springtrello.domain.member.entity.Member;

import java.util.Optional;

public interface MemberQueryDslRepository {
    boolean isWorkspaceMember(Long memberId, Long workspaceId);

    // 멤버 ID로 워크스페이스초대 수락한 멤버 조회
    Optional<Member> findJoinMemberById(Long memberId);

    // 멤버 권한 확인(WORKSPACE)
    boolean hasMemberRoleWORKSPACE(Long id, Long workspaceId);

    // 워크스페이스 ID와 유저ID로 멤버 권한 확인(BOARD, WORKSPACE)
    boolean hasMemberRoleOverREAD_ONLY(Long userId, Long workspaceId);

    // 워크스페이스 ID와 유저ID로 멤버 권한 확인(BOARD, WORKSPACE, READ_ONLY)
    boolean hasMemberRole(Long id, Long workspaceId);


}
