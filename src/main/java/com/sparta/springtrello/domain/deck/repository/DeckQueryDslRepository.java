package com.sparta.springtrello.domain.deck.repository;

import com.sparta.springtrello.domain.deck.dto.response.DeckResponse;
import com.sparta.springtrello.domain.deck.entity.Deck;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DeckQueryDslRepository {
    // 보드ID로 덱 전체 조회 (with 페이징)
    Page<DeckResponse> findAllByBoardId(Long boardId, Pageable pageable);
}
