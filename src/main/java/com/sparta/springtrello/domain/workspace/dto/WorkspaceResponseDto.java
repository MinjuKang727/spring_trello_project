package com.sparta.springtrello.domain.workspace.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkspaceResponseDto {

    private Long workspace_id;
    private String name;
    private String description;

}
