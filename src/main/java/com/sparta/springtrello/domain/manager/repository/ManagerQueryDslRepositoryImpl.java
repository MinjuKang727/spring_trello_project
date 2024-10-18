package com.sparta.springtrello.domain.manager.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.springtrello.common.ErrorStatus;
import com.sparta.springtrello.common.exception.ApiException;
import com.sparta.springtrello.domain.manager.dto.response.ManagerResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sparta.springtrello.domain.card.entity.QCard.card;
import static com.sparta.springtrello.domain.manager.entity.QManager.manager;

@Repository
@RequiredArgsConstructor
public class ManagerQueryDslRepositoryImpl implements ManagerQueryDslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public boolean isMemberManager(Long cardId, Long memberId) {
        if (cardId == null || memberId == null) {
            throw new ApiException(ErrorStatus.BAD_REQUEST_INVALID_CARD_OR_MEMBER);
        }

        Integer fetchOne = jpaQueryFactory
                .selectOne()
                .from(manager)
                .where(
                        managerCardIdEq(cardId)
                                .and(managerMemberIdEq(memberId))
                                .and(managerIsNotDeleted())
                )
                .fetchFirst();

        return fetchOne != null;
    }

    // 카드 ID와 일치하는 조건 정의
    private BooleanExpression managerCardIdEq(Long cardId) {
        return manager.card.id.eq(cardId);
    }

    // 멤버 ID와 일치하는 조건 정의
    private BooleanExpression managerMemberIdEq(Long memberId) {
        return manager.member.id.eq(memberId);
    }

    // isDeleted가 false인 조건 정의
    private BooleanExpression managerIsNotDeleted() {
        return manager.isDeleted.eq(false);
    }

    @Override
    public Page<ManagerResponseDto> getManagers(Long cardId, Pageable pageable) {
        List<ManagerResponseDto> results = jpaQueryFactory
                .select(
                        Projections.constructor(
                                ManagerResponseDto.class,
                                manager.id,
                                manager.member.user.id,
                                manager.member.user.nickname
                        )
                )
                .from(manager)
                .where(
                        managerCardIdEq(cardId)
                                .and(managerIsNotDeleted())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalCount = jpaQueryFactory
                .select(Wildcard.count)
                .from(manager)
                .where(
                        managerCardIdEq(cardId)
                )
                .fetchOne();
        return new PageImpl<>(results,pageable,totalCount);
    }

}
