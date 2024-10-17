package com.sparta.springtrello.common;

import com.sparta.springtrello.common.exception.ApiException;
import com.sparta.springtrello.domain.auth.dto.AuthUser;
import com.sparta.springtrello.domain.card.entity.Card;
import com.sparta.springtrello.domain.card.repository.CardRespository;
import com.sparta.springtrello.domain.deck.entity.Deck;
import com.sparta.springtrello.domain.deck.repository.DeckRepository;
import com.sparta.springtrello.domain.workspace.entity.Workspace;
import com.sparta.springtrello.domain.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class GlobalUtil {

    private final WorkspaceRepository workspaceRepository;
    private final CardRespository cardRespository;
    private final DeckRepository deckRepository;

    private final Long cycle = 1000L * 60 * 60; // 1시간, 1초 * 60 * 60

    public static void hasAuthUser(AuthUser authUser) {
        if(authUser == null) {
            throw new ApiException(ErrorStatus.NOT_FOUND_AUTHENTICATION);
        }
    }

    @Scheduled(fixedDelay = cycle)
    public void EmptyDeleteContents() {
        log.info(":::::::::: 휴지통비우기 :::::::::::");

        // 삭제한 workspace 전부 지우기
        List<Workspace> workspaces = workspaceRepository.findAllByIsDeleted(true);
        workspaceRepository.deleteAll(workspaces);

        // 삭제한 card 전부 지우기
        List<Card> cards = cardRespository.findAllByIsDeleted(true);
        cardRespository.deleteAll(cards);

        // 삭제한 card 전부 지우기
        List<Deck> decks = deckRepository.findAllByIsDeleted(true);
        deckRepository.deleteAll(decks);
    }

}
