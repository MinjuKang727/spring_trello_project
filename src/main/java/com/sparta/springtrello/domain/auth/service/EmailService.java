package com.sparta.springtrello.domain.auth.service;

import com.sparta.springtrello.common.ErrorStatus;
import com.sparta.springtrello.common.exception.ApiException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${spring.mail.username}")
    private String SENDER_EMAIL;

    public void sendEmail(String redisKey, String email) {
        int authNumber = createAuthNumber();
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            String body = "";
            body += "<h3> 가입 인증 번호입니다. </h3>";
            body += "<h1> " + authNumber + " </h1>";

            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(SENDER_EMAIL); // 보내는 이
            helper.setTo(email);          // 받는 이
            helper.setSubject("이메일 인증"); // 이메일 제목
            helper.setText(body, true);
        } catch (MessagingException e) {
            throw new ApiException(ErrorStatus.FAIL_EMAIL_SENDING);
        }

        // 메일 전송
        javaMailSender.send(message);

        // redis 저장
        redisTemplate.opsForValue().set(redisKey, authNumber, 120, TimeUnit.SECONDS);
    }

    public int createAuthNumber() {
        return (int)((Math.random() * 90000)) + 100000;
    }
}
