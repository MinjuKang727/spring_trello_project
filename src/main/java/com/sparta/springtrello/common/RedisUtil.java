package com.sparta.springtrello.common;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ConcurrentHashMap<String, Boolean> ttlSetKeys = new ConcurrentHashMap<>();

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public void withdraw(String key, Object object) {
        redisTemplate.opsForValue().set(key, object);
    }

    public void authEmail(String key, Object object) {
        redisTemplate.opsForValue().set(key, object, 180, TimeUnit.SECONDS);
    }

    public void contentsDelete(String key, Object object) {
        redisTemplate.opsForValue().set(key, object, 60, TimeUnit.MINUTES);
    }

    public void setLogOut(String key, Object object, Long millisecond) {
        redisTemplate.opsForValue().set(key, object, millisecond, TimeUnit.MILLISECONDS);
    }

    public boolean hasKeyLogOut(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }



    //조회수 증가 로직
    public void incrementViews(Long cardId) {
        String key = "card:views:" + cardId;

        Boolean hasKey = redisTemplate.hasKey(key);
        if (Boolean.FALSE.equals(hasKey)) {
            redisTemplate.opsForValue().set(key, 2L);
        } else {
            redisTemplate.opsForValue().increment(key, 1);
        }

        // TTL 설정: 생성일 자정까지 남은 시간 설정 (한 번만 설정)
        ttlSetKeys.computeIfAbsent(key, k -> {
            setExpirationAtMidnight(k);
            return true;
        });
    }

    public Long getViews(Long cardId) {
        String key = "card:views:" + cardId;
        Object value = redisTemplate.opsForValue().get(key);

        if(value == null) {
            return 1L;
        }  else {
            return ((Integer) value).longValue();
        }
    }

    // 자정까지 TTL 설정 메서드
    private void setExpirationAtMidnight(String key) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime midnight = now.toLocalDate().plusDays(1).atStartOfDay();
        Duration duration = Duration.between(now, midnight);
        long secondsUntilMidnight = duration.getSeconds();

        redisTemplate.expire(key, secondsUntilMidnight, TimeUnit.SECONDS);
    }

}
