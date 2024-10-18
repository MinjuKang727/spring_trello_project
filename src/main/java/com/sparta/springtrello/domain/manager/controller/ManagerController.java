package com.sparta.springtrello.domain.manager.controller;

import com.sparta.springtrello.annotation.RequestedMember;
import com.sparta.springtrello.annotation.WorkspaceAccessAuthorize;
import com.sparta.springtrello.annotation.WorkspaceAccessButReadOnlyAuthorize;
import com.sparta.springtrello.common.ApiResponse;
import com.sparta.springtrello.domain.manager.dto.request.GetManagersDto;
import com.sparta.springtrello.domain.manager.dto.response.CardManagerChangedResponseDto;
import com.sparta.springtrello.domain.manager.dto.response.ManagerResponseDto;
import com.sparta.springtrello.domain.manager.service.ManagerService;
import com.sparta.springtrello.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/workspaces/{workspaceId}/boards/{boardId}/decks/{deckId}")
public class ManagerController {
    private final ManagerService managerService;

    //담당자 추가
    @WorkspaceAccessButReadOnlyAuthorize
    @PostMapping("/cards/{cardId}/managers")
    public ResponseEntity<ApiResponse<CardManagerChangedResponseDto>> addCardManager(@PathVariable Long workspaceId,
                                                                                     @PathVariable Long boardId,
                                                                                     @PathVariable Long deckId,
                                                                                     @PathVariable Long cardId,
                                                                                     @RequestedMember Member requestedMember,
                                                                                     @RequestParam Long toAddMemberId) {
        return ResponseEntity.ok(ApiResponse.onSuccess(managerService.addCardManager(workspaceId,
                requestedMember,cardId,toAddMemberId)));
    }

    //담당자 삭제
    @WorkspaceAccessButReadOnlyAuthorize
    @DeleteMapping("/cards/{cardId}/managers")
    public ResponseEntity<ApiResponse<CardManagerChangedResponseDto>> deleteCardManager(@PathVariable Long workspaceId,
                                                                                        @PathVariable Long boardId,
                                                                                        @PathVariable Long deckId,
                                                                                        @PathVariable Long cardId,
                                                                                        @RequestedMember Member member,
                                                                                        @RequestParam Long toRemoveMemberId) {
        return ResponseEntity.ok(ApiResponse.onSuccess(managerService.deleteCardManager(member,cardId,toRemoveMemberId)));
    }

    //어떤 카드의 담당자 조회
    @WorkspaceAccessAuthorize
    @GetMapping("/cards/{cardId}/managers")
    public ResponseEntity<ApiResponse<Page<ManagerResponseDto>>> getCardManager(@PathVariable Long workspaceId,
                                                                               @PathVariable Long boardId,
                                                                               @PathVariable Long deckId,
                                                                               @PathVariable Long cardId,
                                                                               @RequestBody GetManagersDto requestDto) {

        return ResponseEntity.ok(ApiResponse.onSuccess(managerService.getManagers(cardId,requestDto)));
    }
}
