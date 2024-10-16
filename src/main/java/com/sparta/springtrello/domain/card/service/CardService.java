package com.sparta.springtrello.domain.card.service;

import com.sparta.springtrello.domain.card.dto.request.CardCreateRequestDto;
import com.sparta.springtrello.domain.card.dto.request.CardUpdateRequestDto;
import com.sparta.springtrello.domain.card.dto.response.CardCreateResponseDto;
import com.sparta.springtrello.domain.card.dto.response.CardManagerChangedResponseDto;
import com.sparta.springtrello.domain.card.dto.response.CardUpdateResponseDto;
import com.sparta.springtrello.domain.member.entity.Member;

public interface CardService {
    //카드 생성
    CardCreateResponseDto create(Long listId, CardCreateRequestDto requestDto, Member requestedMember);

    //카드 수정
    CardUpdateResponseDto update(Long cardId, CardUpdateRequestDto requestDto);

    //카드 담당자 추가
    CardManagerChangedResponseDto addCardManager(Long workspaceId, Member requestedMember, Long cardId, Long memberId);

    //카드 담당자 제거
    CardManagerChangedResponseDto deleteCardManager(Long workspaceId, Member requestedMember, Long cardId, Long memberId);

    //카드 첨부파일 추가

    //카드 삭제(비활성)

    //카드 검색(마감일)

    //카드 검색(담당자 이름)

    //카드 다른 리스트로 이동


}
