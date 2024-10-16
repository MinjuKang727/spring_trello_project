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

    /**
     * 카드-매니저 연관관계 편의 메서드
     * 이미 해당 카드에 이 객체가 메니저로 있다면 삭제하고 다시 추가
     * @param card 매니저를 추가하고픈 카드
     */
    public void setCard(Card card) {
        if(this.card != null && this.card.getManagerList().contains(this)) {
            this.getCard().getManagerList().remove(this);
        }
        this.card = card;
        card.getManagerList().add(this);
    }

    public void setMember(Member member) {
        this.member = member;
    }

    /**
     * 매니저 논리적 삭제
     * 매니저를 삭제하고자하는 카드의 매니저 리스트에 해당 매니저가 있는지 확인하고
     * 있다면 리스트에서 제거하고 논리적 삭제
     * @param card : 매니저를 제거하고픈 카드
     */
    public void delete(Card card) {
        if (!card.getManagerList().contains(this)) {
            throw new ApiException(ErrorStatus._BAD_REQUEST_NOT_MANAGER);
        }
        card.getManagerList().remove(this);
        this.isDeleted = true;
    }
}
