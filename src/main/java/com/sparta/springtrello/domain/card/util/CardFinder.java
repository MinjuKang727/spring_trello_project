//package com.sparta.springtrello.domain.card.util;
//
//import com.sparta.springtrello.common.ErrorStatus;
//import com.sparta.springtrello.common.exception.ApiException;
//import com.sparta.springtrello.domain.card.entity.Card;
//import com.sparta.springtrello.domain.card.repository.CardRespository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//@RequiredArgsConstructor
//@Transactional(readOnly = true)
//@Component
//public class CardFinder {
//    private final CardRespository cardRespository;
//
//    public Card findById(Long cardId) {
//        return cardRespository.findById(cardId).orElseThrow(
//                ()-> new ApiException(ErrorStatus.NOT_FOUND_CARD)
//        );
//    }
//}
