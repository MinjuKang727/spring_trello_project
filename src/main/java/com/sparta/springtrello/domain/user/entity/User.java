package com.sparta.springtrello.domain.user.entity;

import com.sparta.springtrello.common.Timestamped;
import com.sparta.springtrello.common.dto.AuthUser;
import com.sparta.springtrello.domain.user.enums.UserRole;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@RequiredArgsConstructor
@Table(name = "users")
public class User extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(length = 20)
    private String password;
    @Column(length = 20)
    private String nickname;
    @Column(length = 50, unique = true)
    private String email;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    private boolean isDeleted;
    @Column(length = 20)
    private Long kakaoId;

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

    public static User fromAuthUser(AuthUser authUser) {
        return new User(authUser.getEmail(), authUser.getUserRole());
    }

    private User(String email, UserRole userRole) {
        this.email = email;
        this.userRole = userRole;
    }

    public void delete(User user) {
        this.isDeleted = true;
    }
}
