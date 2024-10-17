package com.sparta.springtrello.domain.auth.service;

import com.sparta.springtrello.common.ErrorStatus;
import com.sparta.springtrello.common.RedisUtil;
import com.sparta.springtrello.common.exception.ApiException;
import com.sparta.springtrello.common.exception.AuthException;
import com.sparta.springtrello.config.JwtUtil;
import com.sparta.springtrello.domain.auth.dto.*;
import com.sparta.springtrello.domain.user.entity.User;
import com.sparta.springtrello.domain.user.enums.UserRole;
import com.sparta.springtrello.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;
    private final RedisUtil redisUtil;

    private static final String AUTH_EMAIL_KEY = "authEmail:";
    private static final String WITHDRAW_KEY = "withdraw:";

    @Value("${admin.key}")
    private String adminKey;

    // 회원가입
    public SignupResponseDto signup(SignupRequestDto request) {

        // DB 이메일 중복 검사
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new ApiException(ErrorStatus.EMAIL_NOT_AVAILABLE);
        }

        // redis 이메일 중복 검사
        String withdrawRedisKey = WITHDRAW_KEY + request.getEmail();
        if(redisUtil.get(withdrawRedisKey) != null) {
            throw new ApiException(ErrorStatus.EMAIL_NOT_AVAILABLE);
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        UserRole userRole;

        // 관리자 키 검사
        if(!request.getAdminKey().isEmpty()) {
            if(adminKey.equals(request.getAdminKey())) {
                userRole = UserRole.of("ROLE_ADMIN");
            } else {
                throw new IllegalArgumentException("관리자 키가 일치하지 않습니다.");
            }
        } else {
            userRole = UserRole.of("ROLE_USER");
        }

        // 이메일 인증
        String redisKey = verifyEmail(request);

        // redis에서 데이터 제거
        redisUtil.delete(redisKey);

        User user = new User(
                request.getEmail(),
                encodedPassword,
                request.getNickname(),
                userRole
        );

        User savedUser = userRepository.save(user);

        String bearerToken = jwtUtil.createToken(savedUser.getId(), savedUser.getEmail(), savedUser.getUserRole());

        return new SignupResponseDto(bearerToken);
    }

    // 로그인
    public SigninResponseDto signin(SigninRequestDto request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() ->
                new ApiException(ErrorStatus.NOT_FOUND_USER));

        if(user.isDeleted()) {
            throw new ApiException(ErrorStatus.BAD_REQUEST_WITHDRAW);
        }

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ApiException(ErrorStatus.NOT_MATCH_PASSWORD);
        }

        String bearerToken = jwtUtil.createToken(user.getId(), user.getEmail(), user.getUserRole());

        return new SigninResponseDto(bearerToken);
    }

    private String verifyEmail(SignupRequestDto requestDto) {
        String email = requestDto.getEmail();
        String redisKey = AUTH_EMAIL_KEY + requestDto.getEmail();
        Integer authNumber = (Integer) redisUtil.get(redisKey);

        // 메일 인증 중인 email 인지 확인
        if(authNumber == null) {
            emailService.sendEmail(redisKey, email);
            throw new ApiException(ErrorStatus.SEND_AUTH_EMAIL);
        }

        // 인증번호 확인
        if(authNumber != requestDto.getAuthNumber()) {
            throw new ApiException(ErrorStatus.FAIL_EMAIL_AUTHENTICATION);
        }

        return redisKey;
    }

    public static User fromAuthUser(AuthUser authUser) {
        return new User(authUser.getId(), authUser.getEmail(), authUser.getUserRole());
    }
}
