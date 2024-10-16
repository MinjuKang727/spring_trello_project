package com.sparta.springtrello.domain.notification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.springtrello.domain.notification.entity.SlackUser;
import com.sparta.springtrello.domain.user.dto.UserResponseDto;
import com.sparta.springtrello.domain.user.entity.User;
import com.sparta.springtrello.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SlackService {

    @Value("${slack.client-id}")
    private String clientId;

    @Value("${slack.client-secret}")
    private String clientSecret;

    private final RestTemplate restTemplate = new RestTemplate();
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public String slackLogin() {
        // 요청 URL 만들기
        String slackAuthURL = UriComponentsBuilder
                .fromUriString("https://slack.com")
                .path("/oauth/v2/authorize")
                .queryParam("client_id", clientId)
                .queryParam("scope", "im:read,chat:write")
                .queryParam("redirect_uri", "https://localhost:8080/auth/login/slack/callback")
                .queryParam("response_type", "code")
                .build()
                .toString();
        return slackAuthURL;
    }

    public UserResponseDto slackLoginCallback(String code) throws JsonProcessingException {
        // 1. "인가 코드"로 "액세스 토큰" 요청
        String accessToken = getToken(code);

        // 2. 토큰으로 카카오 API 호출 : "액세스 토큰"으로 "카카오 사용자 정보" 가져오기
        SlackUser slackUserInfo = getSlackUser(accessToken);

        // 3. 필요시에 회원가입
        User slackUser = registerSlackUserIfNeeded(slackUserInfo);

        return new UserResponseDto(slackUser);
    }

    public String getToken(String code) throws JsonProcessingException {
        // Access Token 요청을 위한 Slack API URL
        String uri = "https://slack.com/api/oauth.v2.access";

        // 요청 Header와 Body 설정
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("redirect_uri", "https://localhost:8080/auth/login/slack/callback");
        body.add("code", code);

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(uri)  // body가 있으므로 post
                .headers(headers)
                .body(body);

        // HTTP 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(
                requestEntity,
                String.class  // 받아 올 데이터 타입  >> 받을 데이터가 토큰 값
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        return jsonNode.get("access_token").asText();
    }

    private SlackUser getSlackUser(String accessToken) throws JsonProcessingException {
        // 요청 URL 만들기
        URI uri = UriComponentsBuilder
                .fromUriString("https://slack.com")
                .path("/api/users.list")
                .encode()
                .build()
                .toUri();

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/json");

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(uri)
                .headers(headers)
                .body(new LinkedMultiValueMap<>());  // body 따로 넣어줄 필요가 없어서 그냥 new 해서 넣어줌.

        // HTTP 요청 보내기
        ResponseEntity<String> response = this.restTemplate.exchange(
                requestEntity,
                String.class
        );

        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        String slackId = jsonNode.get("id").asText();
        String name = jsonNode.get("real_name").asText();
        String email = jsonNode.get("profile")
                                .get("email").asText();

        return new SlackUser(email, name, slackId);
    }

    /**
     * 필요시에 회원가입 혹은 회원 정보 업데이트
     *
     * @param slackUserInfo : 회원 정보가 들어 있는 Dto 객체
     * @return User Entity
     */
    private User registerSlackUserIfNeeded(SlackUser slackUserInfo) {
        // DB 에 중복된 slack Id 가 있는지 확인
        String slackId = slackUserInfo.getSlackId();
        User slackUser = this.userRepository.findBySlackId(slackId).orElse(null);

        if (slackUser == null) {
            // 카카오 사용자 email 동일한 email 가진 회원이 있는지 확인
            String slackEmail = slackUser.getEmail();
            User sameEmailUser = this.userRepository.findByEmail(slackEmail).orElse(null);

            if (sameEmailUser != null) {
                slackUser = sameEmailUser;
                // 기존 회원정보에 카카오 Id 추가
                slackUser = slackUser.slackIdUpdate(slackId);
            } else {
                // 신규 회원가입
                // password: random UUID
                String password = UUID.randomUUID().toString();
                String encodedPassword = this.passwordEncoder.encode(password);

                slackUser = new User(slackUserInfo, encodedPassword);
            }

            this.userRepository.save(slackUser);
        }

        return slackUser;
    }

}
