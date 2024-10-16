package com.sparta.springtrello.domain.user.entity;

import com.sparta.springtrello.domain.auth.dto.AuthUser;
import com.sparta.springtrello.common.Timestamped;
import com.sparta.springtrello.domain.user.enums.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

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

    public User(String email, String password, String nickname, UserRole userRole) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.userRole = userRole;
    }

    private User(Long userId, String email, UserRole userRole) {
        this.id = userId;
        this.email = email;
        this.userRole = userRole;
    }

    public static User fromAuthUser(AuthUser authUser) {
        return new User(authUser.getId(), authUser.getEmail(), authUser.getUserRole());
    }
}
