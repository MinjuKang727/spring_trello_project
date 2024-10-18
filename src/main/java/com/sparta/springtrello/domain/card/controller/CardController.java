package com.sparta.springtrello.domain.card.controller;

import com.sparta.springtrello.annotation.RequestedMember;
import com.sparta.springtrello.annotation.WorkspaceAccessAuthorize;
import com.sparta.springtrello.annotation.WorkspaceAccessButReadOnlyAuthorize;
import com.sparta.springtrello.common.ApiResponse;
import com.sparta.springtrello.domain.card.dto.request.CardCreateRequestDto;
import com.sparta.springtrello.domain.card.dto.request.CardSearchRequestDto;
import com.sparta.springtrello.domain.card.dto.request.CardUpdateRequestDto;
import com.sparta.springtrello.domain.card.dto.response.*;
import com.sparta.springtrello.domain.card.service.CardAttachmentService;
import com.sparta.springtrello.domain.card.service.CardService;
import com.sparta.springtrello.domain.member.entity.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/workspaces/{workspaceId}/boards/{boardId}/decks/{deckId}/")
public class CardController {
    private final CardService cardService;
    private final CardAttachmentService cardAttachmentService;

    /**
     * 카드 생성
     * @param workspaceId : 현재 워크스페이스 Id
     * @param boardId : 계층 구조를 유지하기 위한 url
     * @param deckId : 카드를 추가하고자 하는 Deck Id
     * @param member : 현재 인증된 Member 객체
     * @param requestDto : title을 담고있는 requestDto
     * @return : 카드Id, 카드title 를 담아서 반환하는 responseDto
     */
    @WorkspaceAccessButReadOnlyAuthorize
    @PostMapping("/cards")
    public ResponseEntity<ApiResponse<CardCreateResponseDto>> create(@PathVariable Long workspaceId,
                                                                     @PathVariable Long boardId,
                                                                     @PathVariable Long deckId,
                                                                     @RequestedMember Member member,
                                                                     @RequestBody CardCreateRequestDto requestDto) {

        return ResponseEntity.ok(ApiResponse.onSuccess(cardService.create(deckId,requestDto,member)));
    }

    //카드 수정
    @WorkspaceAccessButReadOnlyAuthorize
    @PutMapping("/cards/{cardId}")
    public ResponseEntity<ApiResponse<CardUpdateResponseDto>> update(@PathVariable Long workspaceId,
                                                                     @PathVariable Long boardId,
                                                                     @PathVariable Long deckId,
                                                                     @PathVariable Long cardId,
                                                                     @RequestedMember Member member,
                                                                     @RequestBody CardUpdateRequestDto requestDto) {
        return ResponseEntity.ok(ApiResponse.onSuccess(cardService.update(member,cardId,requestDto)));
    }

    //카드에 파일 첨부
    @WorkspaceAccessButReadOnlyAuthorize
    @PostMapping("/cards/{cardId}")
    public ResponseEntity<ApiResponse<CardAttachmentResponseDto>> attachFileToFile(@PathVariable Long workspaceId,
                                                                      @PathVariable Long boardId,
                                                                      @PathVariable Long deckId,
                                                                      @PathVariable Long cardId,
                                                                      @RequestedMember Member member,
                                                                      @RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(ApiResponse.onSuccess(cardAttachmentService.attachFileToCard(member,cardId,file)));
    }

    //카드 삭제
    @WorkspaceAccessButReadOnlyAuthorize
    @DeleteMapping("/cards/{cardId}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long workspaceId,
                                                      @PathVariable Long boardId,
                                                      @PathVariable Long deckId,
                                                      @PathVariable Long cardId,
                                                      @RequestedMember Member member) {
        return ResponseEntity.ok(ApiResponse.onSuccess(cardService.delete(member,cardId)));
    }

    //카드 검색
    @WorkspaceAccessAuthorize
    @GetMapping("/cards/search")
    public ResponseEntity<ApiResponse<Page<CardSearchResponseDto>>> search(@PathVariable Long workspaceId,
                                                                           @PathVariable Long boardId,
                                                                           @PathVariable Long deckId,
                                                                           @RequestBody @Valid CardSearchRequestDto requestDto) {
        return ResponseEntity.ok(ApiResponse.onSuccess(cardService.searchCards(requestDto)));
    }

    //카드 단건 상세조회
    @WorkspaceAccessAuthorize
    @GetMapping("/cards/{cardId}")
    public ResponseEntity<ApiResponse<CardDetailsResponseDto>> getCardDetails(@PathVariable Long workspaceId,
                                                                              @PathVariable Long boardId,
                                                                              @PathVariable Long deckId,
                                                                              @PathVariable Long cardId) {
        return ResponseEntity.ok(ApiResponse.onSuccess(cardService.getCardDetails(cardId)));
    }

    //카드 덱 이동
    @PutMapping("/cards/{cardId}/moving")
    public ResponseEntity<ApiResponse<CardDeckMoveResponseDto>> moveCardDeck(@PathVariable Long workspaceId,
                                                                             @PathVariable Long boardId,
                                                                             @PathVariable Long deckId,
                                                                             @PathVariable Long cardId,
                                                                             @RequestParam Long afterDeckId) {
        return ResponseEntity.ok(ApiResponse.onSuccess(cardService.moveCardToAnotherDeck(cardId,afterDeckId)));
    }


}

