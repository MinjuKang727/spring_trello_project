package com.sparta.springtrello.domain.deck.repository;


import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.springtrello.domain.deck.dto.response.DeckResponse;
import com.sparta.springtrello.domain.deck.entity.Deck;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.sparta.springtrello.domain.deck.entity.QDeck.deck;

@RequiredArgsConstructor
public class DeckQueryDslRepositoryImpl implements DeckQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    // 보드ID로 덱 전체 조회 (with 페이징)
    @Override
    public Page<DeckResponse> findAllByBoardId(Long boardId, Pageable pageable) {

        Long total = jpaQueryFactory
                .select(deck.count())
                .from(deck)
                .where(deck.board.id.eq(boardId))
                .fetchFirst();

        if (total == null) {
            total = 0L;
        }

        List<DeckResponse> deckList = jpaQueryFactory
                .select(
                        Projections.constructor(
                                DeckResponse.class,
                                deck.id,
                                deck.name,
                                deck.order
                        )
                )
                .from(deck)
                .where(deck.board.id.eq(boardId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(deckList, pageable, total);

    };
}
