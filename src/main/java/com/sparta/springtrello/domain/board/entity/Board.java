package com.sparta.springtrello.domain.board.entity;

import com.sparta.springtrello.domain.deck.entity.Deck;
import com.sparta.springtrello.domain.workspace.entity.Workspace;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Board {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;
  
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn(name = "deck_order")  // 순서 칼럼 지정
    private List<Deck> deckList = new ArrayList<>();

    @Column(name = "is_deleted")
    private boolean isDeleted;

    private String backgroundcolor;
    private String backgroundimage;

    public Board(String title, Workspace workspace, String backgroundColor, String backgroundImage) {
        this.title = title;
        this.workspace = workspace;
        this.backgroundcolor = backgroundColor;
        this.backgroundimage = backgroundImage;
    }
    public void updateBoard(String title, String backgroundColor, String backgroundImage) {
        this.title = title;
        this.backgroundcolor = backgroundColor;
        this.backgroundimage = backgroundImage;
    }
    public void Deleted() {
        this.isDeleted = true;
    }
}
