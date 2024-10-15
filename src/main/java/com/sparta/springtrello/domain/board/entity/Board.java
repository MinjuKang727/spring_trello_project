package com.sparta.springtrello.domain.board.entity;

import com.sparta.springtrello.domain.deck.entity.Deck;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;


    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn(name = "deck_order")  // 순서 칼럼 지정
    private List<Deck> deckList = new ArrayList<>();

}
