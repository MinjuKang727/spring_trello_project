package com.sparta.springtrello.domain.workspace.repository;

import com.sparta.springtrello.domain.user.entity.User;
import com.sparta.springtrello.domain.workspace.entity.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {

    List<Workspace> findAllByUser(User user);

}
