package com.sparta.springtrello.domain.member.repository;

public interface MemberQueryDslRepository {
    public boolean isWorkspaceMember(Long memberId, Long workspaceId);
}
