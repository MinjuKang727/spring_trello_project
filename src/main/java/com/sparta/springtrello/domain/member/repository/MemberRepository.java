package com.sparta.springtrello.domain.member.repository;

import com.sparta.springtrello.domain.member.entity.Member;
import com.sparta.springtrello.domain.user.entity.User;
import com.sparta.springtrello.domain.workspace.entity.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUserAndWorkspace(User user, Workspace workspace);

}
