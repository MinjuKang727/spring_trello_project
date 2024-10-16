package com.sparta.springtrello.domain.member.service;

import com.sparta.springtrello.domain.member.entity.Member;
import com.sparta.springtrello.domain.member.enums.MemberRole;
import com.sparta.springtrello.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberAuthorizeService {

    // 특정 workspaceId에 대한 Member 객체를 Optional로 반환하는 공통 메서드
    private Optional<Member> findMemberByWorkspaceId(User user, Long workspaceId) {
        if (user == null) {
            return Optional.empty();
        }
        return user.getMemberList().stream()
                .filter(member -> member.getWorkspace().getWorkspace_id().equals(workspaceId))
                .findFirst();
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
