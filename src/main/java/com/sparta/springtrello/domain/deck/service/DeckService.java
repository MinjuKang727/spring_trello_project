package com.sparta.springtrello.domain.deck.service;

import com.sparta.springtrello.common.ErrorStatus;
import com.sparta.springtrello.common.exception.ApiException;
import com.sparta.springtrello.domain.board.entity.Board;
import com.sparta.springtrello.domain.board.repository.BoardRepository;
import com.sparta.springtrello.domain.deck.dto.request.DeckFindAllRequest;
import com.sparta.springtrello.domain.deck.dto.request.DeckMoveRequest;
import com.sparta.springtrello.domain.deck.dto.response.DeckCreateResponse;
import com.sparta.springtrello.domain.deck.dto.response.DeckResponse;
import com.sparta.springtrello.domain.deck.entity.Deck;
import com.sparta.springtrello.domain.deck.repository.DeckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeckService {

    private final DeckRepository deckRepository;
    private final BoardRepository boardRepository;

    /**
     * 덱 생성
     * @param boardId : 덱를 생성할 보드 ID
     * @param deckName : 생성할 덱 이름
     * @return 생성된 덱 정보를 바인딩한 DeckCreateResponse 객체
     */
    @Transactional
    public DeckCreateResponse createDeck(Long boardId, String deckName) {
        Board board = this.boardRepository.findById(boardId).orElseThrow(
                () -> new ApiException(ErrorStatus._NOT_FOUND_BOARD)
        );

        Deck deck = new Deck(deckName, board);
        Deck savedDeck = this.deckRepository.save(deck);

        return new DeckCreateResponse(savedDeck);
    }


    /**
     * 보드 ID로 덱 전체 조회
     * @param request : 덱 조회 조건(워크스페이스 ID, 보드ID, 페이징 page, 페이징 크기)을 바인딩한 DeckFinaAllRequest 객체
     * @return request의 조건으로 조회한 덱 데이터를 DeckFindAllResponse 객체로 바인딩한 후, 페이징한 객체
     */
    public Page<DeckResponse> getDecks(DeckFindAllRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), Sort.by(Sort.Direction.ASC, "order"));

        return this.deckRepository.findAllByBoardId(request.getBoardId(), pageable);
    }


    /**
     * 덱 이동
     * @param request : 이동할 덱가 속한 보드 ID, 이동할 덱 ID, 덱이 이동할 목표 위치를 바인딩한 DeckMoveRequest 객체
     * @return 이동한 덱의 정보를 바인딩한 DeckResponse 객체
     */
    @Transactional
    public DeckResponse moveDeck(DeckMoveRequest request) {
        List<Deck> deckList = this.deckRepository.findAllByBoardId(request.getBoardId());

        if (deckList.isEmpty()) {
            throw new ApiException(ErrorStatus._NO_DECK_IN_BOARD);
        }

        Deck deck = this.deckRepository.findById(request.getDeckId()).orElseThrow(
                () -> new ApiException(ErrorStatus._NOT_FOUND_DECK)
        );

        deckList.remove(deck.getOrder());
        deckList.add(request.getNewOrder() - 1, deck);

        this.deckRepository.flush();

        return new DeckResponse(deckList.get(request.getNewOrder()));

    }


    /**
     * 덱 수정
     * @param deckId : 수정할 덱 ID
     * @param deckName : 수정할 덱 이름
     * @return 수정된 덱 정보를 바인딩 한 DeckResponse 객체
     */
    @Transactional
    public DeckResponse updateDeck(Long deckId, String deckName) {
        Deck deck = this.deckRepository.findById(deckId).orElseThrow(
                () -> new ApiException(ErrorStatus._NOT_FOUND_DECK)
        );

        deck.setName(deckName);

        return new DeckResponse(deck);
    }


    /**
     * 덱 삭제
     * @param deckId : 삭제할 덱 ID
     */
    @Transactional
    public void deleteDeck(Long deckId) {
        Deck deck = this.deckRepository.findById(deckId).orElseThrow(
                () -> new ApiException(ErrorStatus._NOT_FOUND_DECK)
        );

        deck.delete();
    }


}
