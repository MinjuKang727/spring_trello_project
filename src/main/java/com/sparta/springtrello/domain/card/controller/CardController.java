package com.sparta.springtrello.domain.card.controller;

import com.sparta.springtrello.domain.card.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

//    @PostMapping("/workspaces/{workspaceId}/lists/{listId}")
//    public ResponseEntity<ApiResponse<CardCreateResponseDto>> create(@PathVariable Long workspaceId,
//                                                                     @PathVariable Long listId) {
//
//    }
}
