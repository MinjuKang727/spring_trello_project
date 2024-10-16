package com.sparta.springtrello.domain.user.service;

import com.sparta.springtrello.common.RedisUtil;
import com.sparta.springtrello.domain.auth.dto.AuthUser;
import com.sparta.springtrello.domain.user.dto.UserDeleteRequestDto;
import com.sparta.springtrello.domain.user.entity.User;
import com.sparta.springtrello.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisUtil redisUtil;

    private static final String WITHDRAW_KEY = "withdraw:";

    @Transactional
    public String deleteUser(AuthUser authUser, UserDeleteRequestDto requestDto) {

        String redisKey = WITHDRAW_KEY + authUser.getEmail();

        User user = userRepository.findByEmail(authUser.getEmail()).orElseThrow(() ->
            new IllegalArgumentException("유저를 찾을 수 없습니다."));

        if(!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        user.delete();

        // redis 에 영구저장
        redisUtil.withdraw(redisKey, String.valueOf(LocalDate.now()));

        // 로그아웃 기능 구현하기

        //

        return "회원탈퇴가 완료되었습니다. 감사합니다.";
    }
}
