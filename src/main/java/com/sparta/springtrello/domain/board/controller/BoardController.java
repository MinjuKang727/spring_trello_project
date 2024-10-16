package com.sparta.springtrello.domain.board.controller;

import com.sparta.springtrello.common.ApiResponse;
import com.sparta.springtrello.domain.board.dto.BoardRequestDto;
import com.sparta.springtrello.domain.board.dto.BoardResponseDto;
import com.sparta.springtrello.domain.board.service.BoardService;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/workspaces")
public class BoardController {
    @Autowired
    private BoardService boardService;

    //보드 생성하기
    @PostMapping("/{workspacesId}/boards")
    public ResponseEntity<ApiResponse<BoardResponseDto>> createBoard(@RequestBody BoardRequestDto boardRequestDto) {
       BoardResponseDto  createdBoard = boardService.createBoard(boardRequestDto);

        return ResponseEntity.ok(ApiResponse.onSuccess(createdBoard));
    }

    // 특정 워크스페이스의 모든 보드 조회
    @GetMapping("/{workspacesId}/boards")
    public ResponseEntity<ApiResponse<List<BoardResponseDto>>> getBoardByWorkspaceId(@PathVariable Long workspacesId){
        List<BoardResponseDto> boards = boardService.getBoardsByWorkspaceId(workspacesId);

        return ResponseEntity.ok(ApiResponse.onSuccess(boards));
    }

    // 특정보드 조회
    @GetMapping("/{workspacesId}/boards/{boardId}")
    public ResponseEntity<ApiResponse<BoardResponseDto>> getBoardById(@PathVariable Long boardId){
        BoardResponseDto board = boardService.getBoardById(boardId);

        return ResponseEntity.ok(ApiResponse.onSuccess(board));
    }

    // 보드 수정
    @PutMapping("/{workspacesId}/boards/{boardId}")
    public ResponseEntity<ApiResponse<BoardResponseDto>> updateBoardById(@PathVariable Long workspacesId,Long boardId ,@RequestBody BoardRequestDto boardRequestDto){
        BoardResponseDto updateBoard = boardService.updateBoard(boardId, boardRequestDto);

        return ResponseEntity.ok(ApiResponse.onSuccess(updateBoard));
    }

    // 보드 삭제
    @DeleteMapping("/{workspacesId}/boards/{boardId}")
    public ResponseEntity<ApiResponse<Void>> deleteBoard(@PathVariable Long workspacesId, @PathVariable Long boardId){
        boardService.deleteBoard(boardId);

        return ResponseEntity.ok(ApiResponse.onSuccess(null));
    }

}
