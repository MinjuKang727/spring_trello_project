package com.sparta.springtrello.domain.workspace.repository;

import com.sparta.springtrello.domain.user.entity.User;
import com.sparta.springtrello.domain.workspace.entity.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {

    List<Workspace> findAllByUser(User user);

    @Query("SELECT w FROM Workspace w JOIN FETCH w.user WHERE w.isDeleted = false")
    Optional<Workspace> findWorkspaceById(Long workspaceId);
}
