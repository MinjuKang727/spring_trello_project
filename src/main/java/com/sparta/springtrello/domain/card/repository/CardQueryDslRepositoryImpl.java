package com.sparta.springtrello.domain.card.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.springtrello.domain.card.dto.request.CardSearchRequestDto;
import com.sparta.springtrello.domain.card.dto.response.CardSearchResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

import static com.sparta.springtrello.domain.card.entity.QCard.card;
import static com.sparta.springtrello.domain.manager.entity.QManager.manager;

@Repository
@RequiredArgsConstructor
public class CardQueryDslRepositoryImpl implements CardQueryDslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<CardSearchResponseDto> search(CardSearchRequestDto requestDto, Pageable pageable) {
        List<CardSearchResponseDto> results = jpaQueryFactory
                .select(
                        Projections.constructor(
                                CardSearchResponseDto.class,
                                card.id,
                                card.title,
                                manager.countDistinct()
                        )
                )
                .from(card)
                .leftJoin(manager).fetchJoin().on(manager.card.id.eq(card.id), manager.isDeleted.eq(false))
                .where(
                        titleContains(requestDto.getTitle()),
                        beforeDeadline(requestDto.getDeadline()),
                        managerNicknameContains(requestDto.getManagerNickname()),
                        cardNotDeleted()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .groupBy(card.id)
                .orderBy(card.id.desc())
                .fetch();

        Long totalCount = jpaQueryFactory
                .select(Wildcard.count)
                .from(card)
                .where(
                        titleContains(requestDto.getTitle()),
                        beforeDeadline(requestDto.getDeadline()),
                        managerNicknameContains(requestDto.getManagerNickname()),
                        cardNotDeleted()
                )
                .fetchOne();

        return new PageImpl<>(results, pageable, totalCount);
    }

    private BooleanExpression titleContains(String title) {
        return title != null ? card.title.containsIgnoreCase(title) : null;
    }

    private BooleanExpression beforeDeadline(Date deadline) {
        return deadline != null ? card.deadline.loe(deadline) : null;
    }

    private BooleanExpression managerNicknameContains(String managerNickname) {
        return managerNickname != null ? manager.member.user.nickname.containsIgnoreCase(managerNickname) : null;
    }

    private BooleanExpression cardNotDeleted() {
        return card.isDeleted.eq(false);
    }

}
