package com.sparta.springtrello.workspace.repository;

import com.sparta.springtrello.workspace.entity.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {


//    List<Workspace> findAllByUserId(Long userId);
}
