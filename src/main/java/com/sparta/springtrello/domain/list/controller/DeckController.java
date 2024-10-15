package com.sparta.springtrello.domain.list.controller;

import com.sparta.springtrello.common.ApiResponse;
import com.sparta.springtrello.domain.list.dto.request.DeckFindAllRequest;
import com.sparta.springtrello.domain.list.dto.response.DeckCreateResponse;
import com.sparta.springtrello.domain.list.service.DeckService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class DeckController {

    private final DeckService deckService;

    /**
     * 리스트 생성
     * @param boardId : 리스트를 생성할 보드 ID
     * @param listName : 생성할 리스트 이름
     * @return ResponseEntity : Status Code - 200 / body - 생성된 리스트 정보를 바인딩한 ListResponse 객체
     */
    @PostMapping("/workspaces/{workspaceId}/boards/{boadId}/lists")
    public ResponseEntity<ApiResponse<DeckCreateResponse>> createList(
            @PathVariable(name = "boardId") Long boardId,
            @RequestBody String listName
                                                        ) {
        return ResponseEntity
                .status(200)
                .body(
                        ApiResponse.createSuccess(
                                "리스트 생성 성공",
                                HttpStatus.OK.value(),
                                this.deckService.createList(boardId, listName)
                        )
                );
    }

    /**
     * 리스트 전체 조회
     * @param request : 리스트 조회 조건(워크스페이스 ID, 보드ID, 페이징 page, 페이징 크기)을 바인딩 한 ListFinaAllRequest 객체
     * @return ResponseEntity : Status Code - 200 / body - 리스트 조회 결과를 바인딩하여 페이징한 Page<ListResponse> 객체
     */
    @GetMapping("/workspaces/{workspaceId}/boards/{boadId}/lists")
    public ResponseEntity<ApiResponse<Page<DeckCreateResponse>>> getLists(DeckFindAllRequest request) {
        return ResponseEntity
                .status(200)
                .body(
                        ApiResponse.createSuccess(
                                "리스트 전체 조회 성공",
                                HttpStatus.OK.value(),
                                this.deckService.getLists(request)
                        )
                );
    }


    /**
     * 리스트 순서 변경
     * @param boardId : 이동할 리스트가 속한 보드 ID
     * @param newOrder : 리스트가 이동할 목표 위치(인덱스)
     * @return ResponseEntity : Status Code - 200 / body - 이동한 리스트의 정보를 바인딩한 ListResponse 객체
     */
    @PutMapping("/workspaces/{workspaceId}/boards/{boadId}/lists/{listId}/orders/{newOrder}")
    public ResponseEntity<ApiResponse<DeckCreateResponse>> moveList(
            @PathVariable(name = "boardId") Long boardId,
            @PathVariable(name = "newOrder") int newOrder
    ) {
        return ResponseEntity
                .status(200)
                .body(
                        ApiResponse.createSuccess(
                                "리스트 순서 변경 성공",
                                HttpStatus.OK.value(),
                                this.deckService.moveList(boardId, newOrder)
                        )
                );
    }


    /**
     * 리스트 수정
     * @param listId : 수정할 리스트 ID
     * @param listName : 수정할 리스트 이름
     * @return ResponseEntity : Status Code - 200 / body - 수정된 리스트 정보를 바인딩 한 ListResponse 객체
     */
    @PutMapping("/workspaces/{workspaceId}/boards/{boadId}/lists/{listId}")
    public ResponseEntity<ApiResponse<DeckCreateResponse>> updateList(
            @PathVariable(name = "listId") Long listId,
            @RequestBody String listName
    ) {
        return ResponseEntity
                .status(200)
                .body(
                        ApiResponse.createSuccess(
                                "리스트 수정 성공",
                                HttpStatus.OK.value(),
                                this.deckService.updateList(listId, listName)
                        )
                );
    }

    /**
     * 리스트 삭제
     * @param listId : 삭제할 리스트 ID
     * @return ResponseEntity : Status Code - 200
     */
    @DeleteMapping("/workspaces/{workspaceId}/boards/{boadId}/lists/{listId}")
    public ResponseEntity<ApiResponse<Void>> deleteList(@RequestParam(name = "listId") Long listId) {
        this.deckService.deleteList(listId);
        return ResponseEntity
                .status(204)
                .body(
                       ApiResponse.createSuccess(
                               "리스트 삭제 성공",
                               HttpStatus.OK.value(),
                               null
                       )
                );
    }
}
