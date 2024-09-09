package com.hhhh.dodream.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class UserRedisService {
    private static final String AUTH_CODE_PREFIX = "authCode:";
    private static final String EMAIL_CHECK_PREFIX = "emailChecked:";
    private static final long AUTH_CODE_EXPIRE_TIME = 300;
    private static final long EMAIL_CHECK_EXPIRE_TIME = 1;
    private final RedisTemplate<String, String> redisTemplate;

    public void saveAuthCode(String email, Integer authCode) {
        ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(AUTH_CODE_PREFIX + email, String.valueOf(authCode), Duration.ofSeconds(AUTH_CODE_EXPIRE_TIME));
    }

    public boolean checkAuthCode(String email, Integer authCodeFromClient) {
        ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
        String authCodeKey = AUTH_CODE_PREFIX + email;
        Integer authCodeInServer =  Integer.valueOf(valueOperations.get(authCodeKey));
        if (authCodeInServer == null || !authCodeInServer.equals(authCodeFromClient)) return false;
        redisTemplate.delete(authCodeKey);
        valueOperations.set(EMAIL_CHECK_PREFIX + email, "1", Duration.ofDays(EMAIL_CHECK_EXPIRE_TIME));
        return true;
    }

    public boolean isEmailChecked(String email) {
        ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
        String checkedObject = valueOperations.get(EMAIL_CHECK_PREFIX + email);
        if (checkedObject == null) return false;
        return true;
    }
}