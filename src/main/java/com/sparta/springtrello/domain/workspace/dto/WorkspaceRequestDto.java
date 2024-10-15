package com.sparta.springtrello.domain.workspace.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WorkspaceRequestDto {

    private String name;
    private String description;

}
