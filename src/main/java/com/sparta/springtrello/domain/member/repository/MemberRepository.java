package com.sparta.springtrello.domain.member.repository;

import com.sparta.springtrello.domain.member.entity.Member;
import com.sparta.springtrello.domain.member.enums.InvitationStatus;
import com.sparta.springtrello.domain.user.entity.User;
import com.sparta.springtrello.domain.workspace.entity.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberQueryDslRepository {
    @Query("SELECT m FROM Member m WHERE m.user = :user AND m.workspace = :workspace AND m.invitationStatus = :status")
    Optional<Member> findAcceptedMember(@Param("user") User user, @Param("workspace") Workspace workspace, @Param("status") InvitationStatus status);


    Optional<Member> findByUserAndWorkspace(User user, Workspace workspace);
}
