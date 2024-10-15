package com.sparta.springtrello.domain.list.service;

import com.sparta.springtrello.domain.list.dto.request.DeckFindAllRequest;
import com.sparta.springtrello.domain.list.dto.response.DeckCreateResponse;
import com.sparta.springtrello.domain.list.repository.DeckRepository;
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
     * 리스트 생성
     * @param boardId : 리스트를 생성할 보드 ID
     * @param listName : 생성할 리스트 이름
     * @return 생성된 리스트 정보를 바인딩한 ListCreateResponse 객체
     */
    @Transactional
    public DeckCreateResponse createList(Long boardId, String listName) {
        return null;
    }


    /**
     * 보드 ID로 리스트 전체 조회
     * @param request : 리스트 조회 조건(워크스페이스 ID, 보드ID, 페이징 page, 페이징 크기)을 바인딩한 ListFinaAllRequest 객체
     * @return request의 조건으로 조회한 리스트 데이터를 ListFindAllResponse 객체로 바인딩한 후, 페이징한 객체
     */
    public Page<DeckCreateResponse> getLists(DeckFindAllRequest request) {
        return null;
    }


    /**
     * targetIndex로 리스트 이동
     * @param boardId : 이동할 리스트가 속한 보드 ID
     * @param newOrder : 리스트가 이동할 목표 위치(인덱스)
     * @return 이동한 리스트의 정보를 바인딩한 ListResponse 객체
     */
    public DeckCreateResponse moveList(Long boardId, int newOrder) {
        return null;
    }


    /**
     * 리스트 수정
     * @param listId : 수정할 리스트 ID
     * @param listName : 수정할 리스트 이름
     * @return 수정된 리스트 정보를 바인딩 한 ListResponse 객체
     */
    public DeckCreateResponse updateList(Long listId, String listName) {
        return null;
    }


    /**
     * 리스트 삭제
     * @param listId : 삭제할 리스트 ID
     */
    public void deleteList(Long listId) {
    }


}
