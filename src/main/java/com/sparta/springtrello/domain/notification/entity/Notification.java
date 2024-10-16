package com.sparta.springtrello.domain.notification.entity;

import com.sparta.springtrello.common.Timestamped;
import com.sparta.springtrello.domain.card.entity.Card;
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
    private NotificationCategory category;
    private String message;
    private Boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    private Card card;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Notification(NotificationCategory category, String message, Workspace workspace, Member member) {
        this.category = category;
        this.message = message;
        this.workspace = workspace;
        this.member = member;
    }

    public Notification(NotificationCategory category, String message, Card card, Member member) {
        this.category = category;
        this.message = message;
        this.card = card;
        this.member = member;
    }
}


