package com.sparta.springtrello.domain.manager.repository;

public interface ManagerQueryDslRepository {
    boolean isMemberManager(Long cardId, Long memberId);
}
