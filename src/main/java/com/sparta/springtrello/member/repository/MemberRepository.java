package com.sparta.springtrello.member.repository;

import com.sparta.springtrello.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
