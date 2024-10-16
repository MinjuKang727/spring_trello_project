package com.sparta.springtrello.domain.deck.entity;

import com.sparta.springtrello.domain.board.entity.Board;
import com.sparta.springtrello.domain.card.entity.Card;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Deck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deck_id")
    private Long id;
    @Column(nullable = false, length = 50)
    private String name;
    @Column(name = "deck_order", nullable = false)
    private int order;
    private boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(mappedBy = "deck", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Card> cardList = new ArrayList<>();

    public Deck(String name, Board board) {
        this.name = name;
        this.board = board;
    }

    public List<Card> getCardList() {
        return this.cardList;
    }


    public void delete() {
        this.isDeleted = true;
    }
}
