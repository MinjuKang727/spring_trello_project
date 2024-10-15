package com.sparta.springtrello.domain.board.entity;

import jakarta.persistence.*;
import java.util.ArrayList;

@Entity
public class Board {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Deck> Deck = new ArrayList<>();

    private String backgroundcolor;
    private String backgroundimage;


}
