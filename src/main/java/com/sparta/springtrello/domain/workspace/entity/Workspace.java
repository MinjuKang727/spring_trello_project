package com.sparta.springtrello.domain.workspace.entity;


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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workspace_id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column
    private Boolean is_deleted = false;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;

//    @OneToMany(mappedBy = "workspace", cascade = CascadeType.ALL)
//    private List<Board> boardList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "workspace", cascade = CascadeType.ALL)
//    private List<Member> memberList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "workspace", cascade = CascadeType.ALL)
//    private List<Notification> notificationList = new ArrayList<>();

//    public Workspace(String name, String description, User user) {
//        this.name = name;
//        this.description = description;
//        this.user = user;
//    }

    public Workspace(String name, String description) {
        this.name = name;
        this.description = description;
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
        is_deleted = true;
    }

}
