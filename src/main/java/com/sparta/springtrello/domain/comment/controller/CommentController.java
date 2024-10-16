package com.sparta.springtrello.domain.comment.controller;

import com.sparta.springtrello.common.ApiResponse;
import com.sparta.springtrello.common.ErrorStatus;
import com.sparta.springtrello.common.exception.ApiException;
import com.sparta.springtrello.domain.comment.dto.CommentRequestDto;
import com.sparta.springtrello.domain.comment.dto.CommentResponseDto;
import com.sparta.springtrello.domain.comment.entity.Comment;
import com.sparta.springtrello.domain.comment.repository.CommentRepository;
import com.sparta.springtrello.domain.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cards")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentRepository commentRepository;

    @PostMapping("/{cardId}/comments")
    public ResponseEntity<ApiResponse<CommentResponseDto>> createComment(@PathVariable Long cardId, @RequestBody CommentRequestDto commentRequestDto){
        CommentResponseDto responseDto = commentService.createComment(cardId,commentRequestDto);
        return ResponseEntity.ok(ApiResponse.onSuccess(responseDto));
    }

    @PutMapping("/{cardId}/comments/{commentId}")
    public ResponseEntity<ApiResponse<CommentResponseDto>> updateComment(
            @PathVariable Long cardId,
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto commentRequestDto) {
        //댓글확인
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ApiException(ErrorStatus._NOT_FOUND_COMMENT));

        CommentResponseDto responseDto = commentService.updateComment(commentId, commentRequestDto);
        return ResponseEntity.ok(ApiResponse.onSuccess(responseDto));
    }

    @DeleteMapping("/{cardId}/comments/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(@PathVariable Long commentId){

        commentService.deleteComment(commentId);
        return ResponseEntity.ok(ApiResponse.onSuccess(null));
    }
}
