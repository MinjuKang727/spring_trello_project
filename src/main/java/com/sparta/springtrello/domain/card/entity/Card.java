package com.sparta.springtrello.domain.card.entity;

import com.sparta.springtrello.common.Timestamped;
import com.sparta.springtrello.domain.card.dto.request.CardUpdateRequestDto;
import com.sparta.springtrello.domain.deck.entity.Deck;
import com.sparta.springtrello.domain.manager.entity.Manager;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "card")
public class Card  extends Timestamped {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id", nullable = false)
    private Long id;

    @NotNull
    private String title;
    private String contents;
    private String attachment;
    private Date deadline;
    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deck_id")
    private Deck deck;

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<Manager> managerList = new ArrayList<>();

    public Card(String title) {
        this.title = title;
    }

    public void update(CardUpdateRequestDto requestDto) {
        if (requestDto.getTitle() != null) {
            this.title = requestDto.getTitle();
        }
        if (requestDto.getContents() != null) {
            this.contents = requestDto.getContents();
        }
        if (requestDto.getDeadline() != null) {
            this.deadline = requestDto.getDeadline();
        }
    }

    public void setDeck(Deck deck) {
        if(this.deck!=null && this.deck.getCardList().contains(this)) {
            this.getDeck().getCardList().remove(this);
        }
        this.deck = deck;
        deck.getCardList().add(this);
    }

    public void delete() {
        this.isDeleted = true;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachment = attachmentUrl;
    }
}
