package com.sparta.springtrello.domain.manager.repository;

import com.sparta.springtrello.domain.manager.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager,Long>, ManagerQueryDslRepository {
    @Query("SELECT m FROM Manager m WHERE m.member.id = :memberId AND m.isDeleted = false")
    Optional<Manager> findByMemberId(@Param("memberId") Long memberId);
}
