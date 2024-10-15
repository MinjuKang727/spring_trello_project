package com.sparta.springtrello.common.domain.board.entity;

import com.sparta.springtrello.common.domain.list.entity.Deck;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;


    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn(name = "list_order")  // 순서 칼럼 지정
    private List<Deck> deckList = new ArrayList<>();

}
