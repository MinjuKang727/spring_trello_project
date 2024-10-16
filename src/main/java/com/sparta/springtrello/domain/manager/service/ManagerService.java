package com.sparta.springtrello.domain.manager.service;

import com.sparta.springtrello.common.ErrorStatus;
import com.sparta.springtrello.common.exception.ApiException;
import com.sparta.springtrello.domain.card.dto.response.CardManagerChangedResponseDto;
import com.sparta.springtrello.domain.card.entity.Card;
import com.sparta.springtrello.domain.card.repository.CardRespository;
import com.sparta.springtrello.domain.card.util.CardFinder;
import com.sparta.springtrello.domain.manager.entity.Manager;
import com.sparta.springtrello.domain.manager.repository.ManagerRepository;
import com.sparta.springtrello.domain.manager.util.ManagerUtil;
import com.sparta.springtrello.domain.member.entity.Member;
import com.sparta.springtrello.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManagerService {
    private final ManagerRepository managerRepository;
    private final MemberRepository memberRepository;
    private final CardFinder cardFinder;
    private final CardRespository cardRespository;
    private final ManagerUtil managerUtil;

    //담당자 추가
    public CardManagerChangedResponseDto addCardManager(Long workspaceId,
                                                        Member requestedMember,
                                                        Long cardId,
                                                        Long memberId) {
        Card card = cardFinder.findById(cardId);

        //요청한 멤버가 해당 카드의 매니저인지
        if (!isManager(card,requestedMember)) {
            throw new ApiException(ErrorStatus.FORBIDDEN_NOT_MANAGER);
        }

        //추가해주려는 멤버가 해당 워크스페이스의 멤버인지
        Member foundMember = memberRepository.findById(memberId).orElseThrow(
                () -> new ApiException(ErrorStatus.NOT_FOUND_MEMBER)
        );

        if (!workspaceId.equals(foundMember.getWorkspace().getId())) {
            throw new ApiException(ErrorStatus.BAD_REQUEST_NOT_MEMBER);
        }

        //담당자 등록
        managerUtil.createManager(card, foundMember);

        return new CardManagerChangedResponseDto(cardId,
                card.getTitle(),
                foundMember.getUser().getId(),
                foundMember.getUser().getNickname());
    }

    //담당자 삭제
    /*
        1. 카드를 불러온다
        2. 요청한 사람이 카드의 매니저인지 확인
        4. 카드의 매니저 삭제
     */
    public CardManagerChangedResponseDto deleteCardManager(Member requestedMember, Long cardId, Long memberId) {
        Card card = cardFinder.findById(cardId);

        if (!isManager(card,requestedMember)) {
            throw new ApiException(ErrorStatus.FORBIDDEN_NOT_MANAGER);
        }

        Manager foundManager = managerRepository.findByMemberId(memberId).orElseThrow(
                () -> new ApiException(ErrorStatus.BAD_REQUEST_NOT_MANAGER)
        );

        if (!foundManager.getCard().equals(card)) {
            throw new ApiException(ErrorStatus.BAD_REQUEST_NOT_MANAGER);
        }

        foundManager.delete(card);
        managerRepository.save(foundManager);
        cardRespository.save(card);
        return new CardManagerChangedResponseDto(cardId,
                card.getTitle(),
                foundManager.getMember().getUser().getId(),
                foundManager.getMember().getUser().getNickname());
    }

    //요청 멤버가 해당 카드의 매니저인지 확인
    private boolean isManager(Card card, Member requestedMember) {
        return card.getManagerList().stream()
                .anyMatch(manager -> manager.getMember().getId()
                        .equals(requestedMember.getId()));
    }

}

