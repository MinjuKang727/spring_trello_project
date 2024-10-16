package com.sparta.springtrello.domain.manager.util;

import com.sparta.springtrello.common.ErrorStatus;
import com.sparta.springtrello.common.exception.ApiException;
import com.sparta.springtrello.domain.card.entity.Card;
import com.sparta.springtrello.domain.manager.entity.Manager;
import com.sparta.springtrello.domain.manager.repository.ManagerQueryDslRepository;
import com.sparta.springtrello.domain.manager.repository.ManagerRepository;
import com.sparta.springtrello.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ManagerUtil {
    private final ManagerRepository managerRepository;
    private final ManagerQueryDslRepository managerQueryDslRepository;

    //담당자 등록 기능 메서드
    public void createManager(Card card, Member member) {
        Manager manager = new Manager(card,member);
        managerRepository.save(manager);
    }

    //요청한 유저가 요청한 카드의 매니저인지 검증
    public void validateCardManager(Member member, Card card) {
        if(!managerQueryDslRepository.isMemberManager(card.getId(),member.getId())) {
            throw new ApiException(ErrorStatus.FORBIDDEN_NOT_MANAGER);
        }
    }
}
