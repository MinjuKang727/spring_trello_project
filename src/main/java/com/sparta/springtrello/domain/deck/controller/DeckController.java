package com.sparta.springtrello.domain.deck.controller;

import com.sparta.springtrello.common.ApiResponse;
import com.sparta.springtrello.domain.deck.dto.request.DeckFindAllRequest;
import com.sparta.springtrello.domain.deck.dto.request.DeckMoveRequest;
import com.sparta.springtrello.domain.deck.dto.response.DeckCreateResponse;
import com.sparta.springtrello.domain.deck.dto.response.DeckResponse;
import com.sparta.springtrello.domain.deck.service.DeckService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
public class DeckController {

    private final DeckService deckService;

    /**
     * 덱 생성
     * @param boardId : 덱를 생성할 보드 ID
     * @param deckName : 생성할 덱 이름
     * @return ApiResponse : message - "덱 생성 성공"/ Status Code - 200 / date - 생성된 덱 정보를 바인딩한 DeckResponse 객체
     */
    @PostMapping("/workspaces/{workspaceId}/boards/{boardId}/decks")
    public ResponseEntity<ApiResponse<DeckCreateResponse>> createDeck(
            @PathVariable(name = "boardId") Long boardId,
            @RequestBody @NotBlank String deckName
                                                        ) {
        return ResponseEntity
                .status(200)
                .body(
                        ApiResponse.onSuccess(
                                this.deckService.createDeck(boardId, deckName)
                        )
                );
    }

    /**
     * 덱 전체 조회
     * @param request : 덱 조회 조건(워크스페이스 ID, 보드ID, 페이징 page, 페이징 크기)을 바인딩 한 DeckFinaAllRequest 객체
     * @return ApiResponse : message - "덱 전체 조회 성공"/ Status Code - 200 / data - 덱 조회 결과를 바인딩하여 페이징한 Page<DeckResponse> 객체
     */
    @GetMapping("/workspaces/{workspaceId}/boards/{boadId}/decks")
    public ResponseEntity<ApiResponse<Page<DeckResponse>>> getDecks(DeckFindAllRequest request) {
        return ResponseEntity
                .status(200)
                .body(
                        ApiResponse.onSuccess(
                                this.deckService.getDecks(request)
                        )
                );
    }


    /**
     * 덱 순서 변경
     * @param request : 이동할 덱가 속한 보드 ID, 이동할 덱 ID, 덱이 이동할 목표 위치를 바인딩한 DeckMoveRequest 객체
     * @return ApiResponse : message - "덱 순서 변경 성공"/ Status Code - 200 / data - 이동한 덱의 정보를 바인딩한 DeckResponse 객체
     */
    @PutMapping("/workspaces/{workspaceId}/boards/{boadId}/decks/{deckId}/orders/{newOrder}")
    public ResponseEntity<ApiResponse<DeckResponse>> moveDeck(DeckMoveRequest request) {
        return ResponseEntity
                .status(200)
                .body(
                        ApiResponse.onSuccess(
                                this.deckService.moveDeck(request)
                        )
                );
    }


    /**
     * 덱 수정
     * @param deckId : 수정할 덱 ID
     * @param deckName : 수정할 덱 이름
     * @return ApiResponse : message - "덱 수정 성공"/ Status Code - 200 / data - 수정된 덱 정보를 바인딩 한 DeckResponse 객체
     */
    @PutMapping("/workspaces/{workspaceId}/boards/{boadId}/decks/{deckId}")
    public ResponseEntity<ApiResponse<DeckResponse>> updateDeck(
            @PathVariable(name = "deckId") Long deckId,
            @RequestBody @NotBlank String deckName
    ) {
        return ResponseEntity
                .status(200)
                .body(
                        ApiResponse.onSuccess(
                                this.deckService.updateDeck(deckId, deckName)
                        )
                );
    }

    /**
     * 덱 삭제
     * @param deckId : 삭제할 덱 ID
     * @return ApiResponse : message - "덱 삭제 성공"/ Status Code - 200/ data - null
     */
    @DeleteMapping("/workspaces/{workspaceId}/boards/{boadId}/decks/{deckId}")
    public ResponseEntity<ApiResponse<Void>> deleteDeck(@PathVariable(name = "deckId") Long deckId) {
        this.deckService.deleteDeck(deckId);

        return ResponseEntity
                .status(204)
                .body(
                       ApiResponse.onSuccess(
                               null
                       )
                );
    }
}
