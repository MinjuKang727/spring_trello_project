package com.sparta.springtrello.common.domain.list.repository;

import com.sparta.springtrello.common.domain.list.entity.Deck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeckRepository extends JpaRepository<Deck, Long>, DeckQueryDslRepository {
}
