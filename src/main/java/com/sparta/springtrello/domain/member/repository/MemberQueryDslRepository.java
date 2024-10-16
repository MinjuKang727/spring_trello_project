package com.sparta.springtrello.domain.member.repository;

public interface MemberQueryDslRepository {
    boolean isWorkspaceMember(Long memberId, Long workspaceId);
}
