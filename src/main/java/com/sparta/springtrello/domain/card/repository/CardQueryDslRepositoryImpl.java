package com.sparta.springtrello.domain.card.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.springtrello.domain.card.dto.request.CardSearchRequestDto;
import com.sparta.springtrello.domain.card.dto.response.CardDetailsResponseDto;
import com.sparta.springtrello.domain.card.dto.response.CardSearchResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

import static com.sparta.springtrello.domain.card.entity.QCard.card;
import static com.sparta.springtrello.domain.comment.entity.QComment.comment;
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
                        searchConditions(requestDto)
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
                        searchConditions(requestDto)
                )
                .fetchOne();

        return new PageImpl<>(results, pageable, totalCount);
    }

    private BooleanExpression[] searchConditions(CardSearchRequestDto requestDto) {
        return new BooleanExpression[]{
                titleContains(requestDto.getTitle()),
                beforeDeadline(requestDto.getDeadline()),
                managerNicknameContains(requestDto.getManagerNickname()),
                cardsInBoard(requestDto.getBoardId()),
                cardNotDeleted()
        };
    }

    @Override
    public CardDetailsResponseDto getCardDetails(Long cardId) {
        return jpaQueryFactory
                .select(
                        Projections.fields(
                                CardDetailsResponseDto.class,
                                card.id.as("cardId"),
                                card.title.as("title"),
                                card.contents.as("contents"),
                                card.deadline.as("deadline"),
                                card.attachment.as("attachment"),
                                manager.id.count().as("managerCount"),
                                comment.id.count().as("commentCount")
                        ))
                .from(card)
                .leftJoin(manager).on(manager.card.id.eq(card.id))
                .leftJoin(comment).on(comment.card.id.eq(card.id))
                .where(cardIdEq(cardId)
                        .and(cardNotDeleted()))
                .groupBy(card.id)
                .fetchOne();
    }

    private BooleanExpression cardIdEq(Long cardId) {
        return card.id.eq(cardId);
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

    private BooleanExpression cardsInBoard(Long boardId) {
        return boardId != null ? card.deck.board.id.eq(boardId) : null;
    }




}
