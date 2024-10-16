package com.sparta.springtrello.domain.card.controller;

import com.sparta.springtrello.common.ApiResponse;
import com.sparta.springtrello.domain.card.dto.response.CardCreateResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CardController {



    @PostMapping("/workspaces/{workspaceId}/lists/{listId}")
    public ResponseEntity<ApiResponse<CardCreateResponseDto>> create(@PathVariable Long workspaceId,
                                                                     @PathVariable Long listId) {
        return null;
    }
}
