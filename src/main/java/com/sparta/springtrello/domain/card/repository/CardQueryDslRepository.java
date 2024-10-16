package com.sparta.springtrello.domain.card.repository;

import com.sparta.springtrello.domain.card.dto.request.CardSearchRequestDto;
import com.sparta.springtrello.domain.card.dto.response.CardSearchResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CardQueryDslRepository {
    Page<CardSearchResponseDto> search(CardSearchRequestDto requestDto, Pageable pageable);
}
