package com.sparta.springtrello.domain.manager.repository;

public interface ManagerQueryDslRepository {
    public boolean isMemberManager(Long cardId, Long memberId);
}
