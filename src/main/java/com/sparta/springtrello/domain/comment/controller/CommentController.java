package com.sparta.springtrello.domain.comment.controller;

import com.sparta.springtrello.common.ApiResponse;
import com.sparta.springtrello.domain.comment.dto.CommentRequestDto;
import com.sparta.springtrello.domain.comment.dto.CommentResponseDto;
import com.sparta.springtrello.domain.comment.entity.Comment;
import com.sparta.springtrello.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cards")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성
    @PostMapping("/{cardId}/comments")
    public ResponseEntity<ApiResponse<CommentResponseDto>> createComment(@PathVariable Long cardId, @RequestBody CommentRequestDto commentRequestDto){
        CommentResponseDto responseDto = commentService.createComment(cardId,commentRequestDto);
        return ResponseEntity.ok(ApiResponse.onSuccess(responseDto));
    }

    // 댓글 수정
    @PutMapping("/{cardId}/comments/{commentId}")
    public ResponseEntity<ApiResponse<CommentResponseDto>> updateComment(
            @PathVariable Long cardId,
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto commentRequestDto) {
        //댓글확인
        Comment comment = commentService.getComment(commentId);

        CommentResponseDto responseDto = commentService.updateComment(commentId, commentRequestDto);
        return ResponseEntity.ok(ApiResponse.onSuccess(responseDto));
    }

    // 댓글 삭제
    @DeleteMapping("/{cardId}/comments/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(@PathVariable Long commentId){

        commentService.deleteComment(commentId);
        return ResponseEntity.ok(ApiResponse.onSuccess(null));
    }
}
