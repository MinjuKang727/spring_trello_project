package com.sparta.springtrello.domain.notification.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.springtrello.common.ApiResponse;
import com.sparta.springtrello.domain.notification.dto.request.SlackNotificationRequest;
import com.sparta.springtrello.domain.notification.dto.response.SlackMessageResponse;
import com.sparta.springtrello.domain.notification.service.SlackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SlackController {

    private final SlackService slackService;

    /**
     * 컨텐츠 CRUD 알람 전송
     * @PathVariable workspaceId : 이벤트 발생 워크스페이스 ID
     * @RequestParam category : 이벤트 발생 컨텐츠 분류
     * @ReuqestParam contentId : 이벤트 발생 컨텐츠 ID
     * @RequestBody message : 이벤트 메세지
     * @return
     */
    @PostMapping("/workspaces/{workspaceId}/notifications")
    public ResponseEntity<ApiResponse<SlackMessageResponse>> sendNotification(SlackNotificationRequest request) throws JsonProcessingException {
        return ResponseEntity
                .status(201)
                .body(
                        ApiResponse.onSuccess(this.slackService.sendNotification(request))
                );
    }
}
