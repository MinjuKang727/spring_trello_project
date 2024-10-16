package com.sparta.springtrello.domain.comment.entity;

import com.sparta.springtrello.common.Timestamped;
import com.sparta.springtrello.domain.card.entity.Card;
import com.sparta.springtrello.domain.member.entity.Member;
import com.sparta.springtrello.domain.workspace.entity.Workspace;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Comment extends Timestamped {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    private String contents;

    private boolean isDeleted = false;

    public Comment(Member member, Card card, String contents){
        this.member = member;
        this.card = card;
        this.contents = contents;
        this.isDeleted = false;
    }

    public void updateContents(String contents) {
        this.contents = contents;
    }

    public void delete(){
        this.isDeleted = true;
    }
}