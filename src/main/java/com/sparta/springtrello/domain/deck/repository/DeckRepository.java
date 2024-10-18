package com.sparta.springtrello.domain.deck.repository;

import com.sparta.springtrello.domain.deck.dto.response.DeckResponse;
import com.sparta.springtrello.domain.deck.entity.Deck;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeckRepository extends JpaRepository<Deck, Long>, DeckQueryDslRepository {

    @Query("SELECT d FROM Deck d WHERE d.board.id = :boardId AND d.isDeleted = false ORDER BY d.order ASC")
    List<Deck> findAllByBoardId(Long boardId);

    @Query("SELECT d FROM Deck d WHERE d.id = :deckId AND d.isDeleted = false")
    Optional<Deck> findDeckById(Long deckId);

    @Query("SELECT count(d) + 1 FROM Deck d WHERE d.board.id = :boardId AND d.isDeleted = false")
    int getDeckOrder(Long boardId);

}
