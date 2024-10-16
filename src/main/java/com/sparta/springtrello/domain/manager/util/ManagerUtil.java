package com.sparta.springtrello.domain.manager.util;

import com.sparta.springtrello.common.ErrorStatus;
import com.sparta.springtrello.common.exception.ApiException;
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

    //매니저 Id로 매니저 찾기
    public Manager findById(Long managerId) {
        return managerRepository.findById(managerId).orElseThrow(
                () -> new ApiException(ErrorStatus.NOT_FOUND_MANAGER)
        );
    }

    //멤버 객체로 매니저 찾기
    public Manager findByMember (Member member) {
        return managerRepository.findByMember(member).orElseThrow(
                ()-> new ApiException(ErrorStatus.NOT_FOUND_MANAGER)
        );
    }

    //해당 멤버가 매니저인지 확인
    public boolean existsManagerByMember(Member member) {
        return managerRepository.existsByMember(member);
    }
}
