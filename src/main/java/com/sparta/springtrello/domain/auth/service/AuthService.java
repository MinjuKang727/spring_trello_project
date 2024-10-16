package com.sparta.springtrello.domain.auth.service;

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

import java.util.regex.Pattern;

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

        String email = request.getEmail();

        // 이메일 유효성 검사
        if(!validateEmail(email)) {
            throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다.");
        }

        // 이메일 중복 검사
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용중인 이메일입니다.");
        }

        String password = request.getPassword();

        // 비밀번호 유효성 검사
        if(!validatePassword(password)) {
            throw new IllegalArgumentException("비밀번호 형식이 올바르지 않습니다.");
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
                email,
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

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AuthException("잘못된 비밀번호입니다.");
        }

        String bearerToken = jwtUtil.createToken(user.getUserId(), user.getEmail(), user.getUserRole());

        return new SigninResponseDto(bearerToken);
    }

    private boolean validateEmail(String email) {
        // 이메일 정규표현식
        // abc._.ab@abc.a.c or abc._.ab@abc.ac
        String emailPattern = "^[\\w.]+@\\w+\\.\\w+(\\.\\w+)?";

        return Pattern.matches(emailPattern, email);
    }

    private boolean validatePassword(String password) {
        // 비밀번호 정규표현식
        // 대소문자 1개이상, 숫자 1개이상, 특수문자 1개이상, 8자리 이상 20자리 이하
        String passwordPattern = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[!@#$%^&*]).{8,20}$";

        return Pattern.matches(passwordPattern, password);
    }

    public static User fromAuthUser(AuthUser authUser) {
        return new User(authUser.getId(), authUser.getEmail(), authUser.getUserRole());
    }
}
