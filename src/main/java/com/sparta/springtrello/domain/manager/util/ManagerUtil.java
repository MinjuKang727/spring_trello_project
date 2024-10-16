package com.sparta.springtrello.domain.manager.util;

import com.sparta.springtrello.domain.card.entity.Card;
import com.sparta.springtrello.domain.manager.entity.Manager;
import com.sparta.springtrello.domain.manager.repository.ManagerRepository;
import com.sparta.springtrello.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ManagerUtil {
    private final ManagerRepository managerRepository;

    //담당자 등록 기능 메서드
    public Manager createManager(Card card, Member member) {
        Manager manager = new Manager();
        manager.setCard(card);
        manager.setMember(member);
        return managerRepository.save(manager);
    }
}
