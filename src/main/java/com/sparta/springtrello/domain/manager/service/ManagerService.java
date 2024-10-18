package com.sparta.springtrello.domain.manager.service;

import com.sparta.springtrello.common.ErrorStatus;
import com.sparta.springtrello.common.exception.ApiException;
import com.sparta.springtrello.domain.manager.dto.request.GetManagersDto;
import com.sparta.springtrello.domain.manager.dto.response.CardManagerChangedResponseDto;
import com.sparta.springtrello.domain.card.entity.Card;
import com.sparta.springtrello.domain.card.repository.CardRepository;
import com.sparta.springtrello.domain.card.util.CardFinder;
import com.sparta.springtrello.domain.manager.dto.response.ManagerResponseDto;
import com.sparta.springtrello.domain.manager.entity.Manager;
import com.sparta.springtrello.domain.manager.repository.ManagerQueryDslRepository;
import com.sparta.springtrello.domain.manager.repository.ManagerRepository;
import com.sparta.springtrello.domain.manager.util.ManagerUtil;
import com.sparta.springtrello.domain.member.entity.Member;
import com.sparta.springtrello.domain.member.repository.MemberQueryDslRepository;
import com.sparta.springtrello.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ManagerService {
    private final ManagerRepository managerRepository;
    private final MemberRepository memberRepository;
    private final CardFinder cardFinder;
    private final ManagerUtil managerUtil;
    private final CardRepository cardRepository;

    //담당자 추가
    public CardManagerChangedResponseDto addCardManager(Long workspaceId,
                                                        Member requestedMember,
                                                        Long cardId,
                                                        Long memberId) {
        Card card = cardFinder.findById(cardId);

        //요청한 멤버가 해당 카드의 매니저인지
        managerUtil.validateCardManager(requestedMember, card);

        //추가해주려는 멤버가 해당 워크스페이스의 멤버인지,
        if(!memberRepository.isWorkspaceMember(memberId,workspaceId)) {
            throw new ApiException(ErrorStatus.BAD_REQUEST_NOT_MEMBER);
        }

        //추가해주려는 멤버가 이미 해당 카드의 매니저인지
        if(managerRepository.isMemberManager(card.getId(),memberId)) {
            throw new ApiException(ErrorStatus.BAD_REQUEST_ALREADY_MANAGER);
        }

        //추가해주려는 멤버 객체
        Member foundMember = memberRepository.findById(memberId).orElseThrow(
                () -> new ApiException(ErrorStatus.NOT_FOUND_MEMBER)
        );

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

        //요청한 멤버가 해당 카드의 매니저인지
        if(!managerRepository.isMemberManager(card.getId(),requestedMember.getId())) {
            throw new ApiException(ErrorStatus.FORBIDDEN_NOT_MANAGER);
        }

        //삭제해주려는 멤버가 해당 카드의 매니저인지
        if(!managerRepository.isMemberManager(card.getId(),memberId)) {
            throw new ApiException(ErrorStatus.BAD_REQUEST_NOT_MANAGER);
        }

        Manager foundManager = managerRepository.findByMemberId(memberId).orElseThrow(
                () -> new ApiException(ErrorStatus.NOT_FOUND_MANAGER)
        );

        foundManager.delete();
        managerRepository.save(foundManager);

        return new CardManagerChangedResponseDto(cardId,
                card.getTitle(),
                foundManager.getMember().getUser().getId(),
                foundManager.getMember().getUser().getNickname());
    }

    //어떤 카드의 매니저 조회
    public Page<ManagerResponseDto> getManagers(Long cardId, GetManagersDto requestDto) {
        Pageable pageable = PageRequest.of(requestDto.getPage()-1, requestDto.getSize());
        return managerRepository.getManagers(cardId, pageable);
    }

}

