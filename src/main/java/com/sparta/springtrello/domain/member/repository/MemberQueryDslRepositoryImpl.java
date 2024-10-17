package com.sparta.springtrello.domain.member.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.springtrello.domain.member.enums.InvitationStatus;
import com.sparta.springtrello.domain.member.enums.MemberRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.sparta.springtrello.domain.member.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberQueryDslRepositoryImpl implements MemberQueryDslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public boolean isWorkspaceMember(Long memberId, Long workspaceId) {
        Integer fetchOne = jpaQueryFactory
                .selectOne()
                .from(member)
                .where(
                        memberIdEq(memberId)
                                .and(workspaceIdEq(workspaceId))
                                .and(isMemberInvited())
                                .and(isMemberRoleReadOnly())
                )
                .fetchFirst();

        return fetchOne != null;
    }

    //MemberId가 같은지
    private BooleanExpression memberIdEq(Long memberId) {
        return member.id.eq(memberId);
    }

    //WorkspaceId가 같은지
    private BooleanExpression workspaceIdEq(Long workspaceId) {
        return member.workspace.id.eq(workspaceId);
    }

    //초대를 수락한 멤버인지
    private BooleanExpression isMemberInvited() {
        return member.invitationStatus.eq(InvitationStatus.ACCEPT);
    }

    //읽기 권한만 있는 건 아닌지
    private BooleanExpression isMemberRoleReadOnly() {
        return member.memberRole.ne(MemberRole.READ_ONLY);
    }
}
