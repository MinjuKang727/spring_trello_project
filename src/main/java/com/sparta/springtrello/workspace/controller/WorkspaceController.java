package com.sparta.springtrello.workspace.controller;


import com.sparta.springtrello.workspace.dto.WorkspaceRequestDto;
import com.sparta.springtrello.workspace.dto.WorkspaceResponseDto;
import com.sparta.springtrello.workspace.service.WorkspaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    // 생성
    @PostMapping("/workspaces")
    public ResponseEntity<WorkspaceResponseDto> createWorkspace(
//            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody WorkspaceRequestDto requestDto
    ) {
        WorkspaceResponseDto responseDto = workspaceService.createWorkspace(requestDto);
        return ResponseEntity.ok(responseDto);
    }


    // 단건 조회
    @GetMapping("/workspaces/{workspaceId}")
    public ResponseEntity<WorkspaceResponseDto> getWorkspace(
//            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long workspaceId
    ) {
        WorkspaceResponseDto response = workspaceService.getWorkspace(workspaceId);
        return ResponseEntity.ok(response);
    }

//    // 전부 조회
//    @GetMapping("/workspaces")
//    public ResponseEntity<List<WorkspaceResponseDto>> getWorkspaces(
////            @AuthenticationPrincipal UserDetailsImpl userDetails
//    ) {
//        List<WorkspaceResponseDto> response = workspaceService.getWorkspaces();
//        return ResponseEntity.ok(response);
//    }

    // 수정
    @PutMapping("/workspaces/{workspaceId}")
    public ResponseEntity<WorkspaceResponseDto> update(
//            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long workspaceId,
            @RequestBody WorkspaceRequestDto requestDto
    ) {
        WorkspaceResponseDto response = workspaceService.update(workspaceId, requestDto);
        return ResponseEntity.ok(response);
    }

    // 삭제
    @DeleteMapping("/workspaces/{workspaceId}")
    public ResponseEntity<Void> delete(
//            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long workspaceId
    ) {
        workspaceService.delete(workspaceId);
        return ResponseEntity.ok().build();
    }




}



