package com.sparta.springtrello.domain.deck.entity;

import com.sparta.springtrello.domain.board.entity.Board;
import com.sparta.springtrello.domain.card.entity.Card;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "deck")
public class Deck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deck_id")
    private Long id;
    @Column(name = "deck_name")
    private String name;
    @Column(name = "deck_order")
    private int order;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(mappedBy = "deck", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Card> cardList = new ArrayList<>();

    // 생성자
    public Deck(String name, Board board) {
        this.name = name;
        this.board = board;
    }

    // Getter
    public List<Card> getCardList() {
        return this.cardList;
    }
}
