package com.sparta.springtrello.domain.card.entity;

import com.sparta.springtrello.common.Timestamped;
import com.sparta.springtrello.domain.card.dto.request.CardUpdateRequestDto;
import com.sparta.springtrello.domain.list.entity.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "card")
public class Card  extends Timestamped {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    private String title;
    private String contents;
    private String attachment;
    private Date deadline;
    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "list_id")
    private List list;

    public Card(String title) {
        this.title = title;
    }

    public void update(CardUpdateRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.deadline = requestDto.getDeadline();
    }

    public void setList(List list) {
        if(this.list!=null && this.list.getCardList().contains(this)) {
           this.getList().getCardList().remove(this);
        }
        this.list = list;
        list.getCardList().add(this);
    }

    public void delete() {
        this.isDeleted = true;
    }
}
