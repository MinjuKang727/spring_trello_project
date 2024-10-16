package com.sparta.springtrello.domain.deck.util;

import com.sparta.springtrello.common.ErrorStatus;
import com.sparta.springtrello.common.exception.ApiException;
import com.sparta.springtrello.domain.deck.entity.Deck;
import com.sparta.springtrello.domain.deck.repository.DeckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DeckFinder {
    private final DeckRepository deckRepository;

    public Deck findById(Long deckId) {
        return deckRepository.findById(deckId).orElseThrow(
                ()->new ApiException(ErrorStatus.NOT_FOUND_DECK)
        );
    }
}
