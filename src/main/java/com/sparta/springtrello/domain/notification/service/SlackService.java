package com.sparta.springtrello.domain.notification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.springtrello.common.ErrorStatus;
import com.sparta.springtrello.common.exception.ApiException;
import com.sparta.springtrello.domain.member.entity.Member;
import com.sparta.springtrello.domain.notification.dto.request.SlackNotificationRequest;
import com.sparta.springtrello.domain.notification.dto.response.SlackMessageResponse;
import com.sparta.springtrello.domain.notification.entity.Notification;
import com.sparta.springtrello.domain.notification.enums.NotificationCategory;
import com.sparta.springtrello.domain.notification.repository.ContentFactory;
import com.sparta.springtrello.domain.notification.repository.NotificationRepository;
import com.sparta.springtrello.domain.user.entity.User;
import com.sparta.springtrello.domain.user.repository.UserRepository;
import com.sparta.springtrello.domain.workspace.entity.Workspace;
import com.sparta.springtrello.domain.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SlackService {

    @Value("${slack.bot-token}")
    private String botToken;

    private final RestTemplate restTemplate = new RestTemplate();

    private final UserRepository userRepository;
    private final WorkspaceRepository workspaceRepository;
    private final ContentFactory contentFactory;
    private final NotificationRepository notificationRepository;

    // 프라이빗 채널 생성 메서드
    @Transactional
    public String createPrivateChannel(Workspace workspace) throws JsonProcessingException {
        String url = UriComponentsBuilder
                .fromUriString("https://slack.com")
                .path("/api/conversations.create")
                .queryParam("scope", "groups:write")
                .build()
                .toString();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(botToken);  // OAuth 토큰 설정
        headers.set("Content-Type", "application/json; charset=utf-8");

        // 요청에 보낼 데이터 (채널 이름과 비공개 여부)
        Map<String, Object> body = new HashMap<>();
        body.put("name", String.format("nextlo0",workspace.getId()));
        body.put("is_private", true);  // 비공개 채널 설정

        // API 요청 보내기
        RequestEntity<Map<String, Object>> requestEntity = RequestEntity
                .post(url)
                .headers(headers)
                .body(body);

        ResponseEntity<String> response = restTemplate.exchange(
                requestEntity,
                String.class);

        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        if (jsonNode.get("ok").asBoolean()) {
            String channelId = jsonNode.get("channel").get("id").asText();
            workspace.updateChannelId(channelId);
            Workspace savedWorkspace = workspaceRepository.save(workspace);

            return savedWorkspace.getSlackChannelId();
        } else {
            throw new ApiException(ErrorStatus.SLACK_CREATE_CHANNEL_FAILED);
        }
    }


    // 유저 슬랙 ID 조회 by 유저
    @Transactional
    public String getSlackId(User user) throws JsonProcessingException {
        String slackId = user.getSlackId();

        if (slackId != null) {
            return slackId;
        }

        String email = user.getEmail();

        String url = UriComponentsBuilder
                .fromUriString("https://slack.com")
                .path("/api/users.lookupByEmail")
                .queryParam("email", email)
                .build()
                .toString();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(botToken);  // OAuth 토큰 설정

        RequestEntity<Void> requestEntity = RequestEntity
                .get(url)
                .headers(headers)
                .build();

        ResponseEntity<String> response = restTemplate.exchange(
                requestEntity,
                String.class);

        // JSON 응답 파싱
        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());

        if (jsonNode.get("ok").asBoolean()) {
            slackId = jsonNode.get("user").get("id").asText();  // Slack User ID 반환
            user.updateSleckId(slackId);
            User savedUser = this.userRepository.save(user);

            return savedUser.getSlackId();
        }

        throw new ApiException(ErrorStatus.NOT_SLACK_USER);
    }

    // 유저 슬랙 채널에 초대
    public void inviteUserToChannel(String slackChannelId, User user) throws JsonProcessingException {
        String slackId = this.getSlackId(user);

        String url = UriComponentsBuilder
                .fromUriString("https://slack.com")
                .path("/api/conversations.invite")
                .build()
                .toString();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(botToken);  // OAuth 토큰 설정

        Map<String, String> body = Map.of(
                "channel", slackChannelId,
                "users", slackId
                );

        RequestEntity<Map<String, String>> requestEntity = RequestEntity
                .post(url)
                .headers(headers)
                .body(body);

        ResponseEntity<String> response = restTemplate.exchange(
                requestEntity,
                String.class);

        // JSON 응답 파싱
        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());

        if (!jsonNode.get("ok").asBoolean()) {
            throw new ApiException(ErrorStatus.SLACK_INVITE_MEMBER_FAILED);
        }
    }

    // 슬랙 채널에 메세지 보내기
    public void slackDirectMessage(String channelId, String message) throws JsonProcessingException {

        String url = UriComponentsBuilder
                .fromUriString("https://slack.com")
                .path("/api/chat.postMessage")
                .build()
                .toString();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(botToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = new HashMap<>();
        body.put("channel", channelId);
        body.put("text", message);

        RequestEntity<Map<String, String>> requestEntity = RequestEntity
                .post(url)  // body가 있으므로 post
                .headers(headers)
                .body(body);

        // HTTP 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(
                requestEntity,
                String.class
        );


        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        if (!jsonNode.get("ok").asBoolean()) {
            log.error(response.getBody());
            throw new ApiException(ErrorStatus.SLACK_SEND_MESSAGE_FAILED);
        }
    }


    @Transactional
    public SlackMessageResponse sendNotification(SlackNotificationRequest request) throws JsonProcessingException {
        Workspace workspace = this.workspaceRepository.findWorkspaceById(request.getWorkspaceId()).orElseThrow(
                () -> new ApiException(ErrorStatus.NOT_FOUND_WORKSPACE)
        );

        NotificationCategory category = request.getCategory();
        Object content = this.contentFactory.getContent(category, request.getContentId());
        String message = this.contentFactory.getMessage(request.getMessage().getMessage(), category, content, workspace);
        String channelId = workspace.getSlackChannelId();

        switch (request.getMessage()) {
            case WORKSPACE_CREATED -> {
                this.createPrivateChannel(workspace);

                User user = workspace.getUser();
                try {
                    this.inviteUserToChannel(channelId, user);
                } catch (ApiException e) {
                    log.error("{} / 초대받지 못한 유저 ID : {}, email : {}",e.getMessage(), user.getId(), user.getEmail());
                }

            }

            case WORKSPACE_JOIN_NEW_MEMBER -> {
                User user = ((Member) content).getUser();

                try {
                    this.inviteUserToChannel(channelId, user);
                } catch (ApiException e) {
                    log.error("{} / 초대받지 못한 유저 ID : {}, email : {}",e.getMessage(), user.getId(), user.getEmail());
                }

            }
        }

        this.slackDirectMessage(channelId, message);

        Notification notification = new Notification(category, message, content, workspace);
        Notification savedNotification = this.notificationRepository.save(notification);

        return new SlackMessageResponse(savedNotification, savedNotification.getCreatedAt());
    }
}
