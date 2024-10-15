package com.sparta.springtrello.domain.deck.repository;

import com.sparta.springtrello.domain.deck.entity.Deck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeckRepository extends JpaRepository<Deck, Long>, DeckQueryDslRepository {
}
