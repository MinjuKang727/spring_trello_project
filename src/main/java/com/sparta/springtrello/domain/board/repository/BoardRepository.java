package com.sparta.springtrello.domain.board.repository;

import com.sparta.springtrello.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByWorkspaceId(Long workspacesId);

    @Query("SELECT b FROM Board b WHERE b.id = :boardId AND b.isDeleted = false")
    Optional<Board> findBoardById(Long boardId);
}
