package com.sparta.springtrello.domain.manager.entity;

import com.sparta.springtrello.common.ErrorStatus;
import com.sparta.springtrello.common.exception.ApiException;
import com.sparta.springtrello.domain.card.entity.Card;
import com.sparta.springtrello.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Manager {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private boolean isDeleted = false;

    public Manager(Card card, Member member) {
        this.card = card;
        this.member = member;
    }

    public void delete(Card card) {
        this.isDeleted = true;
    }
}
