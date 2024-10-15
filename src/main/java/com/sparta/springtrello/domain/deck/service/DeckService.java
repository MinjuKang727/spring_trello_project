package com.sparta.springtrello.domain.deck.service;

import com.sparta.springtrello.domain.deck.dto.response.DeckCreateResponse;
import com.sparta.springtrello.domain.deck.repository.DeckRepository;
import com.sparta.springtrello.domain.deck.dto.request.DeckFindAllRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeckService {

    private final DeckRepository deckRepository;

    /**
     * 덱 생성
     * @param boardId : 덱를 생성할 보드 ID
     * @param deckName : 생성할 덱 이름
     * @return 생성된 덱 정보를 바인딩한 DeckCreateResponse 객체
     */
    @Transactional
    public DeckCreateResponse createDeck(Long boardId, String deckName) {
        return null;
    }


    /**
     * 보드 ID로 덱 전체 조회
     * @param request : 덱 조회 조건(워크스페이스 ID, 보드ID, 페이징 page, 페이징 크기)을 바인딩한 DeckFinaAllRequest 객체
     * @return request의 조건으로 조회한 덱 데이터를 DeckFindAllResponse 객체로 바인딩한 후, 페이징한 객체
     */
    public Page<DeckCreateResponse> getDecks(DeckFindAllRequest request) {
        return null;
    }


    /**
     * 덱 이동
     * @param boardId : 이동할 덱가 속한 보드 ID
     * @param deckId : 이동할 덱 ID
     * @param newOrder : 덱가 이동할 목표 위치(인덱스)
     * @return 이동한 덱의 정보를 바인딩한 DeckResponse 객체
     */
    public DeckCreateResponse moveDeck(Long boardId, Long deckId, int newOrder) {
        return null;
    }


    /**
     * 덱 수정
     * @param deckId : 수정할 덱 ID
     * @param deckName : 수정할 덱 이름
     * @return 수정된 덱 정보를 바인딩 한 DeckResponse 객체
     */
    public DeckCreateResponse updateDeck(Long deckId, String deckName) {
        return null;
    }


    /**
     * 덱 삭제
     * @param deckId : 삭제할 덱 ID
     */
    public void deleteDeck(Long deckId) {
    }


}
