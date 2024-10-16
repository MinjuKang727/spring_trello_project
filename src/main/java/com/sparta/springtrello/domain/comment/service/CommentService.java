package com.sparta.springtrello.domain.comment.service;

import com.sparta.springtrello.common.ErrorStatus;
import com.sparta.springtrello.common.exception.ApiException;
import com.sparta.springtrello.domain.card.entity.Card;
import com.sparta.springtrello.domain.card.repository.CardRespository;
import com.sparta.springtrello.domain.comment.controller.CommentController;
import com.sparta.springtrello.domain.comment.dto.CommentRequestDto;
import com.sparta.springtrello.domain.comment.dto.CommentResponseDto;
import com.sparta.springtrello.domain.comment.entity.Comment;
import com.sparta.springtrello.domain.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CardRespository cardRespository;

    public CommentResponseDto createComment( Long cardId, CommentRequestDto commentRequestDto){
        //카드가 존재하는지 확인
        Card card = cardRespository.findById(cardId).orElseThrow(() -> new ApiException(ErrorStatus.NOT_FOUND_CARD));
        //댓글 생성
        Comment comment = new Comment(null, card, commentRequestDto.getContents());
        commentRepository.save(comment);
        return new CommentResponseDto(card.getId(), card.getTitle(), null, comment.getId(), comment.getContents());
    }

    public CommentResponseDto updateComment(Long commentId, CommentRequestDto commentRequestDto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ApiException(ErrorStatus.NOT_FOUND_COMMENT));

        comment.updateContents(commentRequestDto.getContents());
        commentRepository.save(comment);

        return new CommentResponseDto(comment.getCard().getId(), comment.getCard().getTitle(), null, comment.getId(), comment.getContents());
    }

    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ApiException(ErrorStatus.NOT_FOUND_COMMENT));
        comment.delete();
        commentRepository.save(comment);
    }

    /**
     * 댓글 ID로 댓글 단건 조회
     * @param commentId : 댓글 ID
     * @return 댓글 Entity
     */
    public Comment getComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(()-> new ApiException(ErrorStatus.NOT_FOUND_COMMENT));
    }
}
