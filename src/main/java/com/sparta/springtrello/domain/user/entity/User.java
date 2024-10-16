package com.sparta.springtrello.domain.user.entity;

import com.sparta.springtrello.domain.auth.dto.AuthUser;
import com.sparta.springtrello.common.Timestamped;
import com.sparta.springtrello.domain.member.entity.Member;
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

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(length = 50, unique = true)
    private String email;
    private String password;
    @Column(length = 20)
    private String nickname;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    @ColumnDefault("false")
    private boolean isDeleted;

    @OneToMany(mappedBy = "user")
    List<Member> memberList = new ArrayList<>();

    public User(String email, String password, String nickname, UserRole userRole) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.userRole = userRole;
    }

    public User(Long userId, String email, UserRole userRole) {
        this.userId = userId;
        this.email = email;
        this.userRole = userRole;
    }

    public static User fromAuthUser(AuthUser authUser) {
        return new User(authUser.getId(), authUser.getEmail(), authUser.getUserRole());
    }
}
