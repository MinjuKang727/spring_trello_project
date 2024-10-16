package com.sparta.springtrello.domain.user.entity;

import com.sparta.springtrello.common.Timestamped;
import com.sparta.springtrello.domain.auth.dto.AuthUser;
import com.sparta.springtrello.domain.member.entity.Member;
import com.sparta.springtrello.domain.notification.entity.SlackUser;
import com.sparta.springtrello.domain.user.enums.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@RequiredArgsConstructor
@Table(name = "users")
public class User extends Timestamped {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, unique = true)
    private String email;

    private String password;

    @Column(length = 20)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @ColumnDefault("false")
    private boolean isDeleted;
    @Column(length = 20)
    private Long kakaoId;
    private String slackId;

    public User(String nickname, String email, UserRole userRole, Long kakaoId) {
        this.nickname = nickname;
        this.email = email;
        this.userRole = userRole;
        this.kakaoId = kakaoId;
    }

    @OneToMany(mappedBy = "user")
    List<Member> memberList = new ArrayList<>();

    public User(String email, String password, String nickname, UserRole userRole) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.userRole = userRole;
    }

    public void InsertKakaoId(Long kakaoId) {
        this.kakaoId = kakaoId;
    }

    private User(Long id, String email, UserRole userRole) {
        this.id = id;
        this.email = email;
        this.userRole = userRole;
    }

    public User(SlackUser slackUser, String password) {
        this.email = slackUser.getEmail();
        this.nickname = slackUser.getName();
        this.password = password;
        this.userRole = UserRole.ROLE_USER;
        this.slackId = slackUser.getSlackId();
    }

    public static User fromAuthUser(AuthUser authUser) {
        return new User(authUser.getId(), authUser.getEmail(), authUser.getUserRole());
    }

    public void delete() {
        this.isDeleted = true;
    }

    public User slackIdUpdate(String slackId) {
        this.slackId = slackId;

        return this;
    }
}
