package com.sparta.springtrello.domain.notification.dto.response;

import com.sparta.springtrello.domain.workspace.entity.Workspace;
import lombok.Getter;

@Getter
public class SlackWorkspaceResponseDto {
    private final Long id;
    private final String name;
    private final String slackChannelId;

    public SlackWorkspaceResponseDto(Workspace workspace) {
        this.id = workspace.getId();
        this.name = workspace.getName();
        this.slackChannelId = workspace.getSlackChannelId();
    }
}
