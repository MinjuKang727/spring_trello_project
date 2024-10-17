package com.sparta.springtrello.domain.user.service;

import com.sparta.springtrello.common.ErrorStatus;
import com.sparta.springtrello.common.GlobalUtil;
import com.sparta.springtrello.common.RedisUtil;
import com.sparta.springtrello.common.exception.ApiException;
import com.sparta.springtrello.config.JwtUtil;
import com.sparta.springtrello.domain.auth.dto.AuthUser;
import com.sparta.springtrello.domain.user.dto.UserDeleteRequestDto;
import com.sparta.springtrello.domain.user.entity.User;
import com.sparta.springtrello.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisUtil redisUtil;
    private final JwtUtil jwtUtil;

    private static final String WITHDRAW_KEY = "withdraw:";

    @Transactional
    public String deleteUser(HttpServletRequest httpServletRequest, AuthUser authUser, UserDeleteRequestDto requestDto) {

        GlobalUtil.hasAuthUser(authUser);

        String redisKey = WITHDRAW_KEY + authUser.getEmail();

        User user = userRepository.findByEmail(authUser.getEmail()).orElseThrow(() ->
            new ApiException(ErrorStatus.NOT_FOUND_USER));

        if(!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new ApiException(ErrorStatus.NOT_MATCH_PASSWORD);
        }

        user.delete();

        // redis 에 영구저장
        redisUtil.withdraw(redisKey, String.valueOf(LocalDate.now()));

        // 로그아웃
        logout(httpServletRequest);

        return "회원탈퇴가 완료되었습니다. 감사합니다.";
    }

    public String logout(HttpServletRequest httpServletRequest) {

        String accessToken = httpServletRequest.getHeader("Authorization").substring(7);
        Date expiration = jwtUtil.extractClaims(accessToken).getExpiration();

        String redisKey = "logout:" + accessToken;
        redisUtil.setLogOut(redisKey, "logout", expiration.getTime() - new Date().getTime());

        return "로그아웃이 완료되었습니다.";
    }
}
