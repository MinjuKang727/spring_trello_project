package com.sparta.springtrello.domain.card.service;

import com.sparta.springtrello.domain.card.dto.request.CardCreateRequestDto;
import com.sparta.springtrello.domain.card.dto.request.CardUpdateRequestDto;
import com.sparta.springtrello.domain.card.dto.response.CardAttachmentResponseDto;
import com.sparta.springtrello.domain.card.dto.response.CardCreateResponseDto;
import com.sparta.springtrello.domain.card.dto.response.CardUpdateResponseDto;
import com.sparta.springtrello.domain.card.entity.Card;
import com.sparta.springtrello.domain.card.repository.CardRespository;
import com.sparta.springtrello.domain.card.util.CardFinder;
import com.sparta.springtrello.domain.deck.entity.Deck;
import com.sparta.springtrello.domain.deck.util.DeckFinder;
import com.sparta.springtrello.domain.manager.repository.ManagerRepository;
import com.sparta.springtrello.domain.manager.util.ManagerUtil;
import com.sparta.springtrello.domain.member.entity.Member;
import com.sparta.springtrello.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class CardServiceImpl implements CardService {

    private final CardRespository cardRespository;
    private final CardFinder cardFinder;
    private final MemberRepository memberRepository;
    private final ManagerRepository managerRepository;
    private final DeckFinder deckFinder;
    private final ManagerUtil managerUtil;

    //카드 생성
    @Override
    public CardCreateResponseDto create(Long deckId,
                                        CardCreateRequestDto requestDto,
                                        Member requestedMember) {
        Card card = new Card(requestDto.getTitle());

        //덱에 카드 등록
        Deck deck = deckFinder.findById(deckId);
        card.setDeck(deck);

        //카드 담당자 등록
        managerUtil.createManager(card, requestedMember);

        Card savedCard = cardRespository.save(card);

        return new CardCreateResponseDto(
                savedCard.getId(),
                savedCard.getTitle());
    }

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





}
