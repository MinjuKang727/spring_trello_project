package com.sparta.springtrello.common.domain.list.entity;

import com.sparta.springtrello.common.domain.board.entity.Board;
import jakarta.persistence.*;

@Entity
@Table(name = "lists")
public class Deck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long listId;
    private String listName;
    @Column(name = "list_order")
    private int listOrder;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;
}
