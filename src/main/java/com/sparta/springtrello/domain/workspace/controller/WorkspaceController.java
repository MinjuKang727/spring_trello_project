package com.sparta.springtrello.domain.workspace.controller;


import com.sparta.springtrello.common.ApiResponse;
import com.sparta.springtrello.common.dto.AuthUser;
import com.sparta.springtrello.domain.workspace.dto.WorkspaceRequestDto;
import com.sparta.springtrello.domain.workspace.dto.WorkspaceResponseDto;
import com.sparta.springtrello.domain.workspace.service.WorkspaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    // 생성
    @PostMapping("/workspaces")
    public ApiResponse<WorkspaceResponseDto> createWorkspace(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestBody WorkspaceRequestDto requestDto
    ) {
        WorkspaceResponseDto responseDto = workspaceService.createWorkspace(authUser, requestDto);
        return ApiResponse.onSuccess(responseDto);
    }

    // 단건 조회
    @GetMapping("/workspaces/{workspaceId}")
    public ApiResponse<WorkspaceResponseDto> getWorkspace(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long workspaceId
    ) {
        WorkspaceResponseDto responseDto = workspaceService.getWorkspace(authUser, workspaceId);
        return ApiResponse.onSuccess(responseDto);
    }

    // 전부 조회
    @GetMapping("/workspaces")
    public ApiResponse<List<WorkspaceResponseDto>> getWorkspaces(
            @AuthenticationPrincipal AuthUser authUser
    ) {
        List<WorkspaceResponseDto> responseDto = workspaceService.getWorkspaces(authUser);
        return ApiResponse.onSuccess(responseDto);
    }

    // 수정
    @PutMapping("/workspaces/{workspaceId}")
    public ApiResponse<WorkspaceResponseDto> update(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long workspaceId,
            @RequestBody WorkspaceRequestDto requestDto
    ) {
        WorkspaceResponseDto responseDto = workspaceService.update(authUser, workspaceId, requestDto);
        return ApiResponse.onSuccess(responseDto);
    }

    // 삭제
    @DeleteMapping("/workspaces/{workspaceId}")
    public ApiResponse<Void> delete(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long workspaceId
    ) {
        workspaceService.delete(authUser, workspaceId);
        return ApiResponse.onSuccess(null);
    }




}



