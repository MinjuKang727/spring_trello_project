package com.sparta.springtrello.domain.card.util;

import com.sparta.springtrello.common.ErrorStatus;
import com.sparta.springtrello.common.exception.ApiException;
import com.sparta.springtrello.domain.card.entity.Card;
import com.sparta.springtrello.domain.card.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Component
public class CardFinder {
    private final CardRepository cardRepository;

    public Card findById(Long cardId) {
        return cardRepository.findById(cardId).orElseThrow(
                ()-> new ApiException(ErrorStatus.NOT_FOUND_CARD)
        );
    }
}
