package com.sparta.springtrello.domain.board.entity;

import com.sparta.springtrello.domain.workspace.entity.Workspace;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Entity
@Getter
@NoArgsConstructor
public class Board {

    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

//    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
//    private List<Deck> Deck = new ArrayList<>();

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    private String backgroundColor;
    private String backgroundImage;
    
    public Board(String title, Workspace workspace, String backgroundColor, String backgroundImage) {
        this.title = title;
        this.workspace = workspace;
        this.backgroundColor = backgroundColor;
        this.backgroundImage = backgroundImage;
    }

    public void updateBoard(String title, String backgroundColor, String backgroundImage) {
        this.title = title;
        this.backgroundColor = backgroundColor;
        this.backgroundImage = backgroundImage;
    }

    public void Deleted() {
        this.isDeleted = true;
    }
}
