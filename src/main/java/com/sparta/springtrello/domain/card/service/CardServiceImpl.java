package com.sparta.springtrello.domain.card.service;

import com.sparta.springtrello.common.ErrorStatus;
import com.sparta.springtrello.common.exception.ApiException;
import com.sparta.springtrello.domain.card.dto.request.CardCreateRequestDto;
import com.sparta.springtrello.domain.card.dto.request.CardUpdateRequestDto;
import com.sparta.springtrello.domain.card.dto.response.CardCreateResponseDto;
import com.sparta.springtrello.domain.card.dto.response.CardManagerChangedResponseDto;
import com.sparta.springtrello.domain.card.dto.response.CardUpdateResponseDto;
import com.sparta.springtrello.domain.card.entity.Card;
import com.sparta.springtrello.domain.card.repository.CardRespository;
import com.sparta.springtrello.domain.card.util.CardFinder;
import com.sparta.springtrello.domain.list.entity.List;
import com.sparta.springtrello.domain.list.util.ListFinder;
import com.sparta.springtrello.domain.manager.entity.Manager;
import com.sparta.springtrello.domain.manager.repository.ManagerRepository;
import com.sparta.springtrello.domain.member.entity.Member;
import com.sparta.springtrello.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.integration.IntegrationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CardServiceImpl implements CardService {

    private final CardRespository cardRespository;
    private final CardFinder cardFinder;
    private final ListFinder listFinder;
    private final MemberRepository memberRepository;
    private final ManagerRepository managerRepository;

    //카드 생성
    @Override
    public CardCreateResponseDto create(Long listId,
                                        CardCreateRequestDto requestDto,
                                        Member requestedMember) {
        Card card = new Card(requestDto.getTitle());

        //덱에 카드 등록
        List list = listFinder.findById(listId);
        card.setList(list);

        //카드 담당자 등록
        createManager(card, requestedMember);

        Card savedCard = cardRespository.save(card);

        return new CardCreateResponseDto(
                savedCard.getId(),
                savedCard.getTitle());
    }

    //이 기능은 아직 미완성입니다.
    //카드 수정
    @Override
    public CardUpdateResponseDto update(Long cardId,
                                        CardUpdateRequestDto requestDto) {

        Card card = cardFinder.findById(cardId);
        card.update(requestDto);
        Card savedCard = cardRespository.save(card);
        return new CardUpdateResponseDto(
                savedCard.getId(),
                savedCard.getTitle(),
                savedCard.getContents(),
                savedCard.getDeadline());
    }

    //담당자 추가
    /*
        1. 카드를 불러온다
        2. 요청한 사람이 카드의 매니저인지 확인
        3. 추가하려는 멤버가 요청 workspcae의 member인지 확인
        4. 카드의 매니저 등록
     */
    @Override
    public CardManagerChangedResponseDto addCardManager(Long workspaceId,
                                                        Member requestedMember,
                                                        Long cardId,
                                                        Long memberId) {
        Card card = cardFinder.findById(cardId);

        if(!managerRepository.existsByMember_Id(requestedMember.getId())) {
            throw new ApiException(ErrorStatus.FORBIDDEN_NOT_MANAGER);
        }

        Member foundMember = memberRepository.findById(memberId).orElseThrow(
                () -> new ApiException(ErrorStatus.NOT_FOUND_MEMBER)
        );

        if (!workspaceId.equals(foundMember.getWorkspace().getId())) {
            throw new ApiException(ErrorStatus.BAD_REQUEST_NOT_MEMBER);
        }

        createManager(card, foundMember);

        return new CardManagerChangedResponseDto(cardId,
                card.getTitle(),
                foundMember.getUser().getUserId(),
                foundMember.getUser().getNickname());
    }

    //담당자 등록 기능 메서드
    private Manager createManager(Card card, Member member) {
        Manager manager = new Manager();
        manager.setCard(card);
        manager.setMember(member);
        return managerRepository.save(manager);
    }

    //담당자 삭제
    /*
        1. 카드를 불러온다
        2. 요청한 사람이 카드의 매니저인지 확인
        4. 카드의 매니저 삭제
     */
    @Override
    public CardManagerChangedResponseDto deleteCardManager(Long workspaceId, Member requestedMember, Long cardId, Long memberId) {
        Card card = cardFinder.findById(cardId);

        if(!managerRepository.existsByMember_Id(requestedMember.getId())) {
            throw new ApiException(ErrorStatus.FORBIDDEN_NOT_MANAGER);
        }

        Manager foundManager = managerRepository.findByMemberId(memberId).orElseThrow(
                ()-> new ApiException(ErrorStatus.BAD_REQUEST_NOT_MANAGER)
        );

        if (!foundManager.getCard().equals(card)) {
            throw new ApiException(ErrorStatus.BAD_REQUEST_NOT_MANAGER);
        }

        foundManager.delete(card);
        managerRepository.save(foundManager);
        cardRespository.save(card);
        return new CardManagerChangedResponseDto(cardId,
                card.getTitle(),
                foundManager.getMember().getUser().getUserId(),
                foundManager.getMember().getUser().getNickname());
    }

}
