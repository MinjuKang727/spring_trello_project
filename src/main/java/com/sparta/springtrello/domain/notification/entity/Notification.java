package com.sparta.springtrello.domain.notification.entity;

import com.sparta.springtrello.common.Timestamped;
import com.sparta.springtrello.domain.board.entity.Board;
import com.sparta.springtrello.domain.card.entity.Card;
import com.sparta.springtrello.domain.deck.entity.Deck;
import com.sparta.springtrello.domain.member.entity.Member;
import com.sparta.springtrello.domain.notification.enums.NotificationCategory;
import com.sparta.springtrello.domain.workspace.entity.Workspace;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@NoArgsConstructor
public class Notification extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;
    @Enumerated(EnumType.STRING)
    private NotificationCategory category;
    @Column(length = 255)
    private String message;
    private boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deck_id")
    private Deck deck;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    private Card card;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Notification(NotificationCategory category, String message, Object content, Workspace workspace) {
        this.category = category;
        this.message = message;
        this.workspace = workspace;

        switch (category) {
            case BOARD -> this.board = (Board) content;
            case DECK -> this.deck = (Deck) content;
            case CARD -> this.card = (Card) content;
            case MEMEBER -> this.member = (Member) content;
        }
    }
}


