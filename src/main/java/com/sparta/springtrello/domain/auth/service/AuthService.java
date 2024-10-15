package com.sparta.springtrello.domain.auth.service;

import com.sparta.springtrello.common.exception.AuthException;
import com.sparta.springtrello.config.JwtUtil;
import com.sparta.springtrello.domain.auth.dto.SigninRequestDto;
import com.sparta.springtrello.domain.auth.dto.SigninResponseDto;
import com.sparta.springtrello.domain.auth.dto.SignupRequestDto;
import com.sparta.springtrello.domain.auth.dto.SignupResponseDto;
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

    @Value("${admin.key}")
    private String adminKey;

    // 회원가입
    public SignupResponseDto signup(SignupRequestDto request) {

        // 이메일 중복 검사
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용중인 이메일입니다.");
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

        User user = new User(
                request.getEmail(),
                encodedPassword,
                request.getNickname(),
                userRole
        );

        User savedUser = userRepository.save(user);

        String bearerToken = jwtUtil.createToken(savedUser.getUserId(), savedUser.getEmail(), savedUser.getUserRole());

        return new SignupResponseDto(bearerToken);
    }

    // 로그인
    public SigninResponseDto signin(SigninRequestDto request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() ->
                new IllegalArgumentException("가입되지 않은 유저입니다."));

        if(user.isDeleted()) {
            throw new IllegalArgumentException("탈퇴한 유저입니다.");
        }

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AuthException("잘못된 비밀번호입니다.");
        }

        String bearerToken = jwtUtil.createToken(user.getUserId(), user.getEmail(), user.getUserRole());

        return new SigninResponseDto(bearerToken);
    }
}
