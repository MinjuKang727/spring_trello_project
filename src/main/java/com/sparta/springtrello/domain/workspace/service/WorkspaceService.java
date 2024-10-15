package com.sparta.springtrello.domain.workspace.service;


import com.sparta.springtrello.common.ErrorStatus;
import com.sparta.springtrello.common.dto.AuthUser;
import com.sparta.springtrello.common.exception.ApiException;
import com.sparta.springtrello.domain.member.entity.Member;
import com.sparta.springtrello.domain.member.repository.MemberRepository;
import com.sparta.springtrello.domain.user.entity.User;
import com.sparta.springtrello.domain.user.enums.UserRole;
import com.sparta.springtrello.domain.workspace.dto.WorkspaceRequestDto;
import com.sparta.springtrello.domain.workspace.dto.WorkspaceResponseDto;
import com.sparta.springtrello.domain.workspace.entity.Workspace;
import com.sparta.springtrello.domain.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public WorkspaceResponseDto createWorkspace(AuthUser authUser, WorkspaceRequestDto requestDto) {
        // 권한 확인
        if (authUser.getUserRole() != UserRole.ROLE_ADMIN) {
            throw new ApiException(ErrorStatus._FORBIDDEN_TOKEN);
        }
        User user = User.fromAuthUser(authUser);
        Workspace newWorkspace = new Workspace(
                requestDto.getName(),
                requestDto.getDescription(),
                user
        );
        Workspace savedWorkspace = workspaceRepository.save(newWorkspace);

        return new WorkspaceResponseDto(
                savedWorkspace.getId(),
                savedWorkspace.getName(),
                savedWorkspace.getDescription()
        );

    }

    public WorkspaceResponseDto getWorkspace(AuthUser authUser, Long workspaceId) {

        Workspace workspace = workspaceRepository.findById(workspaceId).orElseThrow(() ->
                new NullPointerException("Not found"));

        // workspace의 멤버 목록에 authUser가 포함되어 있는지 확인
        boolean isMember = workspace.getMemberList().stream()
                .anyMatch(member -> member.getUser().getId().equals(authUser.getId()));

        // authUser가 멤버가 아니면 예외 발생
        if (!isMember) {
            throw new IllegalArgumentException("해당 워크스페이스의 멤버가 아닙니다.");
        }

        return new WorkspaceResponseDto(
                workspace.getId(),
                workspace.getName(),
                workspace.getDescription()
        );
    }

    public List<WorkspaceResponseDto> getWorkspaces(AuthUser authUser) {
        User user = User.fromAuthUser(authUser);
        List<Workspace> workspaces = workspaceRepository.findAllByUser(user);
        List<WorkspaceResponseDto> workspaceResponseDtos = new ArrayList<>();

        for (Workspace workspace : workspaces) {
            WorkspaceResponseDto responseDto = new WorkspaceResponseDto(
                    workspace.getId(),
                    workspace.getName(),
                    workspace.getDescription()
            );
            workspaceResponseDtos.add(responseDto);
        }
        return workspaceResponseDtos;
    }

    @Transactional
    public WorkspaceResponseDto update(AuthUser authUser, Long workspaceId, WorkspaceRequestDto requestDto) {
        // 권한 확인
        if (authUser.getUserRole() != UserRole.ROLE_ADMIN) {
            throw new ApiException(ErrorStatus._FORBIDDEN_TOKEN);
        }
        Workspace workspace = workspaceRepository.findById(workspaceId).orElseThrow(() ->
                new NullPointerException("Not found"));
        workspace.update(
                requestDto.getName(),
                requestDto.getDescription()
        );
        workspaceRepository.save(workspace);
        return new WorkspaceResponseDto(
                workspace.getId(),
                workspace.getName(),
                workspace.getDescription()
        );

    }

    @Transactional
    public void delete(AuthUser authUser, Long workspaceId) {
        // 권한 확인
        if (authUser.getUserRole() != UserRole.ROLE_ADMIN) {
            throw new ApiException(ErrorStatus._FORBIDDEN_TOKEN);
        }
        Workspace workspace = workspaceRepository.findById(workspaceId).orElseThrow(() ->
                new NullPointerException("Not found"));
        workspace.deleteWorkspace();
    }
}
