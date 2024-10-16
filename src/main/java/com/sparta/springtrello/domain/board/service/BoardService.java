package com.sparta.springtrello.domain.board.service;

import com.sparta.springtrello.common.ErrorStatus;
import com.sparta.springtrello.common.exception.ApiException;
import com.sparta.springtrello.domain.board.dto.BoardRequestDto;
import com.sparta.springtrello.domain.board.dto.BoardResponseDto;
import com.sparta.springtrello.domain.board.entity.Board;
import com.sparta.springtrello.domain.board.repository.BoardRepository;

import com.sparta.springtrello.domain.workspace.WorkspaceRepository;
import com.sparta.springtrello.domain.workspace.entity.Workspace;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardService {

    private BoardRepository boardRepository;
    private WorkspaceRepository workspaceRepository;

    public BoardResponseDto createBoard(BoardRequestDto boardRequestDto) {

        //workspaceID가져오기
       Workspace workspace = workspaceRepository.findById(boardRequestDto.getWorkspacesid()).orElseThrow(() -> new ApiException(ErrorStatus._NOT_FOUND_WORKSPACE));
        // 보드 생성하기
       Board board = new Board(boardRequestDto.getTitle(), workspace, boardRequestDto.getBackgroundcolor(), boardRequestDto.getBackgroundimage());
        // 생성한 보드 저장
       Board createBoard = boardRepository.save(board);
       return new BoardResponseDto(createBoard.getId(),
               createBoard.getTitle(),
               boardRequestDto.getWorkspacesid(),
               createBoard.getBackgroundColor(),
               createBoard.getBackgroundImage(),
               createBoard.isDeleted()
       );
    }

    // 특정워크스페이스에 들어있는 보드 조회
    public List<BoardResponseDto> getBoardsByWorkspaceId(Long workspacesId) {
        return boardRepository.findByWorkspaceId(workspacesId).stream()
                .map(board -> new BoardResponseDto(board.getId(),
                        board.getTitle(),
                        workspacesId,
                        board.getBackgroundColor(),
                        board.getBackgroundImage(),
                        board.isDeleted()))
                .collect(Collectors.toList()
                );
    }
    // 특정보드 조회
    public BoardResponseDto getBoardById(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new ApiException(ErrorStatus._NOT_FOUND_BOARD));
        return new BoardResponseDto(board.getId(),
                board.getTitle(),
                board.getWorkspace().getWorkspace_id(),
                board.getBackgroundColor(),
                board.getBackgroundImage(),
                board.isDeleted()
        );
    }
    //보드 수정
    public BoardResponseDto updateBoard(Long boardId, BoardRequestDto boardRequestDto) {

        Board board = boardRepository.findById(boardId).orElseThrow(() -> new ApiException(ErrorStatus._NOT_FOUND_BOARD));

        board.updateBoard(boardRequestDto.getTitle(), boardRequestDto.getBackgroundcolor(), boardRequestDto.getBackgroundimage());

        boardRepository.save(board);
        return new BoardResponseDto(board.getId(),
                board.getTitle(),
                board.getWorkspace().getWorkspace_id(),
                board.getBackgroundColor(),
                board.getBackgroundImage(),
                board.isDeleted()
        );
    }


    public void deleteBoard(Long boardId) {

        Board board = boardRepository.findById(boardId).orElseThrow(() -> new ApiException(ErrorStatus._NOT_FOUND_BOARD));
        board.Deleted();
        boardRepository.save(board);
    }

}
