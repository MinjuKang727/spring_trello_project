package com.sparta.springtrello.domain.manager.repository;

import com.sparta.springtrello.domain.manager.dto.response.ManagerResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ManagerQueryDslRepository {
    boolean isMemberManager(Long cardId, Long memberId);
    Page<ManagerResponseDto> getManagers(Long cardId, Pageable pageable);
}
