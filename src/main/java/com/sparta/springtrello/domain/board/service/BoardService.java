package com.sparta.springtrello.domain.board.service;

import com.sparta.springtrello.common.ErrorStatus;
import com.sparta.springtrello.common.RedisUtil;
import com.sparta.springtrello.common.exception.ApiException;
import com.sparta.springtrello.domain.board.dto.BoardRequestDto;
import com.sparta.springtrello.domain.board.dto.BoardResponseDto;
import com.sparta.springtrello.domain.board.entity.Board;
import com.sparta.springtrello.domain.board.repository.BoardRepository;
import com.sparta.springtrello.domain.workspace.entity.Workspace;
import com.sparta.springtrello.domain.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private BoardRepository boardRepository;
    private WorkspaceRepository workspaceRepository;
    private final RedisUtil redisUtil;

    private static final String BOARD_DELETE_KEY = "board:";

    public BoardResponseDto createBoard(BoardRequestDto boardRequestDto) {

        //workspaceID가져오기
       Workspace workspace = workspaceRepository.findById(boardRequestDto.getWorkspacesid()).orElseThrow(() -> new ApiException(ErrorStatus.NOT_FOUND_WORKSPACE));
        // 보드 생성하기
       Board board = new Board(boardRequestDto.getTitle(), workspace, boardRequestDto.getBackgroundcolor(), boardRequestDto.getBackgroundimage());
        // 생성한 보드 저장
       Board createBoard = boardRepository.save(board);
       return new BoardResponseDto(createBoard.getId(),
               createBoard.getTitle(),
               boardRequestDto.getWorkspacesid(),
               createBoard.getBackgroundcolor(),
               createBoard.getBackgroundimage(),
               createBoard.isDeleted()
       );
    }

    // 특정워크스페이스에 들어있는 보드 조회
    public List<BoardResponseDto> getBoardsByWorkspaceId(Long workspacesId) {
        return boardRepository.findByWorkspaceId(workspacesId).stream()
                .map(board -> new BoardResponseDto(board.getId(),
                        board.getTitle(),
                        workspacesId,
                        board.getBackgroundcolor(),
                        board.getBackgroundimage(),
                        board.isDeleted()))
                .collect(Collectors.toList()
                );
    }
    // 특정보드 조회
    public BoardResponseDto getBoardById(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new ApiException(ErrorStatus.NOT_FOUND_BOARD));
        return new BoardResponseDto(board.getId(),
                board.getTitle(),
                board.getWorkspace().getId(),
                board.getBackgroundcolor(),
                board.getBackgroundimage(),
                board.isDeleted()
        );
    }
    //보드 수정
    public BoardResponseDto updateBoard(Long boardId, BoardRequestDto boardRequestDto) {

        Board board = boardRepository.findById(boardId).orElseThrow(() -> new ApiException(ErrorStatus.NOT_FOUND_BOARD));

        board.updateBoard(boardRequestDto.getTitle(), boardRequestDto.getBackgroundcolor(), boardRequestDto.getBackgroundimage());

        boardRepository.save(board);
        return new BoardResponseDto(board.getId(),
                board.getTitle(),
                board.getWorkspace().getId(),
                board.getBackgroundcolor(),
                board.getBackgroundimage(),
                board.isDeleted()
        );
    }


    public void deleteBoard(Long boardId) {

        Board board = boardRepository.findById(boardId).orElseThrow(() -> new ApiException(ErrorStatus.NOT_FOUND_BOARD));
        board.Deleted();
        boardRepository.save(board);

        // redis에 1시간 저장 후 삭제
        String redisKey = BOARD_DELETE_KEY + boardId;
        redisUtil.contentsDelete(redisKey, board);
    }

}
