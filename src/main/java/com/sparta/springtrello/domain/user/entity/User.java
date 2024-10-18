package com.sparta.springtrello.domain.user.entity;

import com.sparta.springtrello.common.Timestamped;
import com.sparta.springtrello.domain.auth.dto.AuthUser;
import com.sparta.springtrello.domain.member.entity.Member;
import com.sparta.springtrello.domain.user.enums.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@RequiredArgsConstructor
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

    private boolean isDeleted;

    @Column(length = 20)
    private Long kakaoId;
    private String slackId;

    @OneToMany(mappedBy = "user")
    List<Member> memberList = new ArrayList<>();

    public User(String nickname, String email, UserRole userRole, Long kakaoId) {
        this.nickname = nickname;
        this.email = email;
        this.userRole = userRole;
        this.kakaoId = kakaoId;
    }

    public User(String email, String password, String nickname, UserRole userRole) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.userRole = userRole;
    }

    public void InsertKakaoId(Long kakaoId) {
        this.kakaoId = kakaoId;
    }

    public User(Long id, String email, UserRole userRole) {
        this.id = id;
        this.email = email;
        this.userRole = userRole;
    }

    public static User fromAuthUser(AuthUser authUser) {
        return new User(authUser.getId(), authUser.getEmail(), authUser.getUserRole());
    }

    public void delete() {
        this.isDeleted = true;
    }

    public void updateSleckId(String slackId) {
        this.slackId = slackId;
    }
}
