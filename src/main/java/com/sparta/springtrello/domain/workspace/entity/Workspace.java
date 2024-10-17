package com.sparta.springtrello.domain.workspace.entity;


import com.sparta.springtrello.domain.board.entity.Board;
import com.sparta.springtrello.domain.member.entity.Member;
import com.sparta.springtrello.domain.notification.entity.Notification;
import com.sparta.springtrello.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Workspace {

    @Id
    @Column(name = "workspace_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column
    private boolean isDeleted;

    private String slackChannelId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "workspace", cascade = CascadeType.ALL)
    private List<Member> memberList = new ArrayList<>();

    @OneToMany(mappedBy = "workspace", cascade = CascadeType.ALL)
    private List<Board> boardList = new ArrayList<>();

    @OneToMany(mappedBy = "workspace", cascade = CascadeType.ALL)
    private List<Notification> notificationList = new ArrayList<>();

    public Workspace(String name, String description, User user) {
        this.name = name;
        this.description = description;
        this.user = user;
    }


    public void update(String name, String description) {
        if (name != null) {
            this.name = name;
        }
        if (description != null) {
            this.description = description;
        }
    }

    public void deleteWorkspace() {
        isDeleted = true;
    }

    public void updateChannelId(String channelId) {
        this.slackChannelId = channelId;
    }
}
