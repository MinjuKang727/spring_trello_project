package com.sparta.springtrello.domain.manager.controller;

import com.sparta.springtrello.annotation.RequestedMember;
import com.sparta.springtrello.common.ApiResponse;
import com.sparta.springtrello.domain.card.dto.response.CardManagerChangedResponseDto;
import com.sparta.springtrello.domain.manager.service.ManagerService;
import com.sparta.springtrello.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ManagerController {
    private final ManagerService managerService;

    //담당자 추가
    @PostMapping("/workspaces/{workspaceId}/boards/{boardId}/decks/{deckId}/{cardId}/manager")
    public ResponseEntity<ApiResponse<CardManagerChangedResponseDto>> addCardManager(@PathVariable Long workspaceId,
                                                                                     @PathVariable Long boardId,
                                                                                     @PathVariable Long deckId,
                                                                                     @PathVariable Long cardId,
                                                                                     @RequestedMember Member requestedMember,
                                                                                     @RequestParam Long toAddMemberId) {
        return ResponseEntity.ok(ApiResponse.onSuccess(managerService.addCardManager(workspaceId,
                requestedMember,cardId,toAddMemberId)));
    }

    @DeleteMapping("/workspaces/{workspaceId}/boards/{boardId}/decks/{deckId}/{cardId}/manager")
    public ResponseEntity<ApiResponse<CardManagerChangedResponseDto>> deleteCardManager(@PathVariable Long workspaceId,
                                                                                        @PathVariable Long boardId,
                                                                                        @PathVariable Long deckId,
                                                                                        @PathVariable Long cardId,
                                                                                        @RequestedMember Member member,
                                                                                        @RequestParam Long toRemoveMemberId) {
        return ResponseEntity.ok(ApiResponse.onSuccess(managerService.deleteCardManager(member,cardId,toRemoveMemberId)));
    }
}
