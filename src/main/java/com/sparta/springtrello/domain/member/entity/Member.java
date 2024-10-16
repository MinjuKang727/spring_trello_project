package com.sparta.springtrello.domain.member.entity;


import com.sparta.springtrello.domain.member.enums.MemberRole;
import com.sparta.springtrello.domain.user.entity.User;
import com.sparta.springtrello.domain.workspace.entity.Workspace;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // 메뉴 id
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id", nullable = false) // 메뉴 id
    private Workspace workspace;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;


    // 유저 추가해야함
    public Member(Workspace workspace, MemberRole memberRole) {
        this.workspace = workspace;
        this.memberRole = memberRole;
    }

    public void updateRole(MemberRole memberRole) {
        this.memberRole = memberRole;
    }

    public void setUser(User user) {
        if (this.user != null) {
            this.user.getMemberList().remove(this);
        }
        this.user = user;
        if (user != null && !user.getMemberList().contains(this)) {
            user.getMemberList().add(this);
        }
    }


}
