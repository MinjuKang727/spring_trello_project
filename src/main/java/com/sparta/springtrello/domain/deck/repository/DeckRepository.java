package com.sparta.springtrello.domain.deck.repository;

import com.sparta.springtrello.domain.card.entity.Card;
import com.sparta.springtrello.domain.deck.dto.response.DeckResponse;
import com.sparta.springtrello.domain.deck.entity.Deck;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeckRepository extends JpaRepository<Deck, Long> {

    @Query("SELECT d FROM Deck d WHERE d.board.id = :boardId")
    Page<DeckResponse> findAllByBoardId(Long boardId, Pageable pageable);

    @Query("SELECT d FROM Deck d WHERE d.board.id = :boardId ORDER BY d.order ASC")
    List<Deck> findAllByBoardId(Long boardId);

}
