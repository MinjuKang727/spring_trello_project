package com.sparta.springtrello.domain.workspace.service;


import com.sparta.springtrello.domain.workspace.dto.WorkspaceRequestDto;
import com.sparta.springtrello.domain.workspace.dto.WorkspaceResponseDto;
import com.sparta.springtrello.domain.workspace.entity.Workspace;
import com.sparta.springtrello.domain.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepository;

    @Transactional
    public WorkspaceResponseDto createWorkspace(WorkspaceRequestDto requestDto) {
    // 권한 확인
    // User user = User.fromAuthUser(authUser);


        Workspace newWorkspace = new Workspace(
                requestDto.getName(),
                requestDto.getDescription()
        );
        Workspace savedWorkspace = workspaceRepository.save(newWorkspace);

        return new WorkspaceResponseDto(
                savedWorkspace.getId(),
                savedWorkspace.getName(),
                savedWorkspace.getDescription()
        );


    }

    public WorkspaceResponseDto getWorkspace(Long workspaceId) {
        // 권한 확인
        Workspace workspace = workspaceRepository.findById(workspaceId).orElseThrow(() ->
                new NullPointerException("Not found"));

        return new WorkspaceResponseDto(
                workspace.getId(),
                workspace.getName(),
                workspace.getDescription()
        );

    }

//    public List<WorkspaceResponseDto> getWorkspaces() {
//    // 권한 확인
//        List<Workspace> workspaces = workspaceRepository.findAllByUserId();
//        List<WorkspaceResponseDto> workspaceResponseDtos = new ArrayList<>();
//        for (Workspace workspace : workspaces) {
//            WorkspaceResponseDto responseDto = new WorkspaceResponseDto(
//                    workspace.getWorkspace_id(),
//                    workspace.getName(),
//                    workspace.getDescription()
//            );
//                    workspaceResponseDtos.add(responseDto);
//        }
//        return workspaceResponseDtos;
//    }

    @Transactional
    public WorkspaceResponseDto update(Long workspaceId, WorkspaceRequestDto requestDto) {
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
    public void delete(Long workspaceId) {
        Workspace workspace = workspaceRepository.findById(workspaceId).orElseThrow(() ->
                new NullPointerException("Not found"));
        workspace.deleteWorkspace();
    }
}
