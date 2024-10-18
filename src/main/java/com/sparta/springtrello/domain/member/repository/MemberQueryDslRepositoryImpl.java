package com.sparta.springtrello.domain.member.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.springtrello.domain.member.entity.Member;
import com.sparta.springtrello.domain.member.enums.InvitationStatus;
import com.sparta.springtrello.domain.member.enums.MemberRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.sparta.springtrello.domain.user.entity.QUser.user;
import static com.sparta.springtrello.domain.member.entity.QMember.member;
import static com.sparta.springtrello.domain.workspace.entity.QWorkspace.workspace;

@Repository
@RequiredArgsConstructor
public class MemberQueryDslRepositoryImpl implements MemberQueryDslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
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

    // 멤버 ID로 워크스페이스초대 수락한 멤버 조회
    @Override
    public Optional<Member> findJoinMemberById(Long memberId) {

        return Optional.ofNullable(
                jpaQueryFactory
                    .select(member)
                    .from(member)
                    .join(member.user, user).fetchJoin()
                    .join(member.workspace, workspace)
                    .where(
                            member.invitationStatus.eq(InvitationStatus.ACCEPT)
                                    .and(member.user.isDeleted.isFalse())
                    )
                    .fetchOne()
        );
    }


    // 멤버 권한 확인(WORKSPACE)
    @Override
    public boolean hasMemberRoleWORKSPACE(Long userId, Long workspaceId){
        return Boolean.TRUE.equals(jpaQueryFactory
                .select(member.count().eq(1L))
                .from(member)
                .where(member.workspace.id.eq(workspaceId)
                        .and(member.user.id.eq(userId))
                        .and(member.invitationStatus.eq(InvitationStatus.ACCEPT))
                        .and(member.memberRole.eq(MemberRole.WORKSPACE))
                )
                .fetchFirst());

    }


    // 워크스페이스 ID와 유저ID로 멤버 권한 확인(BOARD, WORKSPACE)
    @Override
    public boolean hasMemberRoleOverREAD_ONLY(Long userId, Long workspaceId) {
        return Boolean.TRUE.equals(jpaQueryFactory
                .select(member.count().eq(1L))
                .from(member)
                .where(member.workspace.id.eq(workspaceId)
                        .and(member.user.id.eq(userId))
                        .and(member.invitationStatus.eq(InvitationStatus.ACCEPT)
                        .and(member.memberRole.ne(MemberRole.READ_ONLY))
                        )
                )
                .fetchFirst());
    }


    // 워크스페이스 ID와 유저ID로 멤버 권한 확인(BOARD, WORKSPACE, READ_ONLY)
    @Override
    public boolean hasMemberRole(Long userId, Long workspaceId) {
        return Boolean.TRUE.equals(jpaQueryFactory
                .select(member.count().eq(1L))
                .from(member)
                .where(member.workspace.id.eq(workspaceId)
                        .and(member.user.id.eq(userId))
                        .and(member.invitationStatus.eq(InvitationStatus.ACCEPT))
                )
                .fetchFirst());
    }


}
