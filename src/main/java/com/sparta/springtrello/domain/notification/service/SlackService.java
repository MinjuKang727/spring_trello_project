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
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SlackService {
//
//    @Value("${slack.client-id}")
//    private String clientId;
//
//    @Value("${slack.client-secret}")
//    private String clientSecret;
//
//    @Value("${slack.state}")
//    private String state;

    @Value("${slack.bot-token}")
    private String botToken;

//    @Value("${slack.user-token}")
//    private String userToken;

    private final RestTemplate restTemplate = new RestTemplate();

    private final UserRepository userRepository;
//    private final MemberRepository memberRepository;
    private final WorkspaceRepository workspaceRepository;
//    private final PasswordEncoder passwordEncoder;
    private final ContentFactory contentFactory;
    private final NotificationRepository notificationRepository;


//    public String slackLogin() {
//        // 요청 URL 만들기
//        String slackAuthURL = UriComponentsBuilder
//                .fromUriString("https://slack.com")
//                .path("/oauth/v2/authorize")
//                .queryParam("client_id", clientId)
//                .queryParam("scope", "im:write,chat:write")
//                .queryParam("redirect_uri", "https://localhost:8080/auth/slack/callback")
//                .queryParam("state", state)
//                .queryParam("response_type", "code")
//                .build()
//                .toString();
//        return slackAuthURL;
//    }
//
//    public UserResponseDto slackLoginCallback(String code, String state) throws JsonProcessingException {
//        // 1. "인가 코드"로 "액세스 토큰" 요청
//        String accessToken = getToken(code, state);
//
//        // 2. 토큰으로 카카오 API 호출 : "액세스 토큰"으로 "카카오 사용자 정보" 가져오기
//        SlackUser slackUserInfo = getSlackUser(accessToken);
//
//        // 3. 필요시에 회원가입
//        User slackUser = registerSlackUserIfNeeded(slackUserInfo);
//
//        return new UserResponseDto(slackUser);
//    }
//
//    public String getToken(String code, String state) throws JsonProcessingException {
//        if (!this.state.equals(state)) {
//            throw new ApiException(ErrorStatus.UNAUTHORIZED_INVALID_STATE);
//        }
//
//        // Access Token 요청을 위한 Slack API URL
//        String uri = "https://slack.com/api/oauth.v2.access";
//
//        // 요청 Header와 Body 설정
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        // HTTP Body 생성
//        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
//        body.add("client_id", clientId);
//        body.add("client_secret", clientSecret);
//        body.add("redirect_uri", "https://localhost:8080/auth/slack/callback");
//        body.add("code", code);
//
//        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
//                .post(uri)  // body가 있으므로 post
//                .headers(headers)
//                .body(body);
//
//        // HTTP 요청 보내기
//        ResponseEntity<String> response = restTemplate.exchange(
//                requestEntity,
//                String.class  // 받아 올 데이터 타입  >> 받을 데이터가 토큰 값
//        );
//        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
//        if (jsonNode.get("ok").asBoolean()) {
//            // HTTP 응답 (JSON) -> 액세스 토큰 파싱
//            JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
//            return jsonNode.get("access_token").asText();
//        } else {
//            log.error(response.getBody());
//            throw new ApiException(ErrorStatus.SLACK_ACCESSTOKEN_REQUEST_FAILED);
//        }
//    }
//
//    private SlackUser getSlackUser(String accessToken) throws JsonProcessingException {
//        // 요청 URL 만들기
//        URI uri = UriComponentsBuilder
//                .fromUriString("https://slack.com")
//                .path("/api/users.list")
//                .queryParam("scope", "users:read")
//                .encode()
//                .build()
//                .toUri();
//
//        // HTTP Header 생성
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + accessToken);
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        HttpEntity<String> request = new HttpEntity<>(headers);
//
//        // HTTP 요청 보내기
//        ResponseEntity<String> response = this.restTemplate.exchange(
//                uri,
//                HttpMethod.GET,
//                request,
//                String.class
//        );
//        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
//        if (jsonNode.get("ok").asBoolean()) {
//            log.info("응답 받기 성공");
//            JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
//
//            JsonNode members = jsonNode.get("members");
//
//            for(JsonNode member : members) {
//                String slackId = member.get("id").asText();
//                JsonNode profileNod = member.get("profile");
//
//                if (profileNod != null && profileNod.has("email")) {
//                    String email = profileNod.get("email").asText();
//                    String diplayName = profileNod.get("display_name").asText();
//                }
//            }
//            log.info(member.toString());
//
//            if (member == null) {
//                throw new ApiException(ErrorStatus.NOT_FOUND_USER);
//            }
//
//            String slackId = member.get("id").asText();
//            log.info("slack id : {}", slackId);
//
//            JsonNode profile = member.get("profile");
//            log.info(profile.toString());
//
//            String displayName = profile.get("display_name").asText();
//            log.info("display name : {}", displayName);
//            String email = profile.get("email").asText();
//
//            return new SlackUser(email, displayName, slackId);
//        } else {
//            log.error(response.getBody());
//            throw new ApiException(ErrorStatus.SLACK_FETCH_USER_FAILED);
//        }
//    }
//
//    /**
//     * 필요시에 회원가입 혹은 회원 정보 업데이트
//     *
//     * @param slackUserInfo : 회원 정보가 들어 있는 Dto 객체
//     * @return User Entity
//     */
//    private User registerSlackUserIfNeeded(SlackUser slackUserInfo) {
//        // DB 에 중복된 slack Id 가 있는지 확인
//        String slackId = slackUserInfo.getSlackId();
//        User slackUser = this.userRepository.findBySlackId(slackId).orElse(null);
//
//        if (slackUser == null) {
//            // 카카오 사용자 email 동일한 email 가진 회원이 있는지 확인
//            String slackEmail = slackUser.getEmail();
//            User sameEmailUser = this.userRepository.findByEmail(slackEmail).orElse(null);
//
//            if (sameEmailUser != null) {
//                slackUser = sameEmailUser;
//                // 기존 회원정보에 카카오 Id 추가
//                slackUser = slackUser.slackIdUpdate(slackId);
//            } else {
//                // 신규 회원가입
//                // password: random UUID
//                String password = UUID.randomUUID().toString();
//                String encodedPassword = this.passwordEncoder.encode(password);
//
//                slackUser = new User(slackUserInfo, encodedPassword);
//            }
//
//            this.userRepository.save(slackUser);
//        }
//
//        return slackUser;
//    }



    public void slackDirectMessage(String channelId) throws JsonProcessingException {
        String url = UriComponentsBuilder
                .fromUriString("https://slack.com")
                .path("/api/chat.postMessage")
                .queryParam("scope", "chat:write")
                .build()
                .toString();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(botToken);
        headers.set("Content-Type", "application/json; charset=utf-8");

        Map<String, String> body = new HashMap<>();
        body.put("channel", channelId);
        body.put("text", "test 메세지");

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
        if (jsonNode.get("ok").asBoolean()) {
            log.info("메세지 전송 성공\n{}", response.getBody());
        } else {
            log.error(response.getBody());
            throw new ApiException(ErrorStatus.SLACK_SEND_MESSAGE_FAILED);
        }
    }


//    public void createPrivateChannel() throws JsonProcessingException {
//        String url = UriComponentsBuilder
//                .fromUriString("https://slack.com")
//                .path("/api/conversations.create")
//                .queryParam("scope", "groups:write")
//                .build()
//                .toString();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(botToken);  // OAuth 토큰 설정
//        headers.set("Content-Type", "application/json; charset=utf-8");
//
//        // 요청에 보낼 데이터 (채널 이름과 비공개 여부)
//        Map<String, Object> body = new HashMap<>();
//        body.put("name", "channel1");
//        body.put("is_private", true);  // 비공개 채널 설정
//
//        // API 요청 보내기
//        RequestEntity<Map<String, Object>> requestEntity = RequestEntity
//                .post(url)
//                .headers(headers)
//                .body(body);
//
//        ResponseEntity<String> response = restTemplate.exchange(
//                requestEntity,
//                String.class);
//
//        log.info("요청 보낸 후");
//        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
//        if (jsonNode.get("ok").asBoolean()) {
//            String channelId = jsonNode.get("channel").get("id").asText();
//        } else {
//            throw new ApiException(ErrorStatus.SLACK_CREATE_CHANNEL_FAILED);
//        }
//    }

//    public void createWorkspaceChannel() throws JsonProcessingException, InterruptedException {
//        String channelId = createPrivateChannel();
//        String slackId = getSlackId();
//        inviteUserToChannel(channelId, slackId);
//        slackDirectMessage(channelId);
//    }


//
//    public String createPrivateChannel() throws JsonProcessingException {
//        log.info("createPrivateChannel() 실행");
//        String url = UriComponentsBuilder
//                .fromUriString("https://slack.com")
//                .path("/api/conversations.create")
//                .queryParam("scope", "chat:write")
//                .build()
//                .toString();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(botToken);  // OAuth 토큰 설정
//        headers.set("Content-Type", "application/json; charset=utf-8");
//
//        // 요청에 보낼 데이터 (채널 이름과 비공개 여부)
//        Map<String, Object> body = new HashMap<>();
//        body.put("name", "nextlo12");
//        body.put("is_private", true);  // 비공개 채널 설정
//
//        // API 요청 보내기
//        RequestEntity<Map<String, Object>> requestEntity = RequestEntity
//                .post(url)
//                .headers(headers)
//                .body(body);
//
//        ResponseEntity<String> response = restTemplate.exchange(
//                requestEntity,
//                String.class);
//
//        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
//        if (jsonNode.get("ok").asBoolean()) {
//            String channelId = jsonNode.get("channel").get("id").asText();
//            log.info("channel id : {}", channelId);
//
//            return channelId;
//        } else {
//            throw new ApiException(ErrorStatus.SLACK_CREATE_CHANNEL_FAILED);
//        }
//    }
//    public String getSlackId() throws JsonProcessingException {
//
//        String url = UriComponentsBuilder
//                .fromUriString("https://slack.com")
//                .path("/api/users.lookupByEmail")
//                .queryParam("email", "minjukang727@gmail.com")
//                .build()
//                .toString();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(botToken);  // OAuth 토큰 설정
//
//        RequestEntity<Void> requestEntity = RequestEntity
//                .get(url)
//                .headers(headers)
//                .build();
//
//        ResponseEntity<String> response = restTemplate.exchange(
//                requestEntity,
//                String.class);
//
//        // JSON 응답 파싱
//        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
//
//        if (jsonNode.get("ok").asBoolean()) {
//            String slackId = jsonNode.get("user").get("id").asText();  // Slack User ID 반환
//            log.info("slack id : {}", slackId);
//            return slackId;
//        }
//
//        throw new ApiException(ErrorStatus.NOT_SLACK_USER);
//    }
//    public String getBotId() throws JsonProcessingException {
//
//        String url = UriComponentsBuilder
//                .fromUriString("https://slack.com")
//                .path("/api/auth.test")
//                .build()
//                .toString();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(botToken);  // OAuth 토큰 설정
//
//        RequestEntity<Void> requestEntity = RequestEntity
//                .get(url)
//                .headers(headers)
//                .build();
//
//        ResponseEntity<String> response = restTemplate.exchange(
//                requestEntity,
//                String.class);
//
//        // JSON 응답 파싱
//        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
//
//        if (jsonNode.get("ok").asBoolean()) {
//            String botId = jsonNode.get("bot_id").asText();  // Slack User ID 반환
//            log.info("bot id : {}", botId);
//            return botId;
//        }
//
//        throw new ApiException(ErrorStatus.NOT_SLACK_USER);
//    }
///////////////////////////////////////////////////////////////


//    public String getDMChannelId(String slackId) throws JsonProcessingException {
//        log.info("getDMChannelId() 메서드 실행");
//        String url = UriComponentsBuilder
//                .fromUriString("https://slack.com")
//                .path("/api/conversations.open")
//                .build()
//                .toString();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(botToken);  // OAuth 토큰 설정
//        headers.set("Content-Type", "application/json;");
//
//        Map<String, String> body = Map.of("users", slackId);
//
//        RequestEntity<Void> requestEntity = RequestEntity
//                .get(url)
//                .headers(headers)
//                .build();
//
//        ResponseEntity<String> response = restTemplate.exchange(
//                requestEntity,
//                String.class);
//
//        // JSON 응답 파싱
//        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
//
//        if (jsonNode.get("ok").asBoolean()) {
//            return jsonNode.get("channel").get("id").asText();  // Slack User ID 반환
//        }
//
//        throw new ApiException(ErrorStatus.SLACK_DM_CHANNEL_CONNECTION_FAILED);
//    }



///////////////////////////////////////////////////////
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
        Workspace workspace = this.workspaceRepository.findById(request.getWorkspaceId()).orElseThrow(
                () -> new ApiException(ErrorStatus.NOT_FOUND_WORKSPACE)
        );

        NotificationCategory category = request.getCategory();
        Object content = this.contentFactory.getContent(category, request.getContentId());
        String message = this.contentFactory.getMessage(request.getMessage().getMessage(), category, content);
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
