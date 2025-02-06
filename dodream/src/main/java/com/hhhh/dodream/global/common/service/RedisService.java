package com.hhhh.dodream.global.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final StringRedisTemplate redisTemplate;

    public void setKeyWithExpiration(String key, String value, long expiredTime) {
        redisTemplate.opsForValue().set(key, value, Duration.ofMillis(expiredTime));
    }

    public void deleteKey(String key) {
        redisTemplate.delete(key);
    }

    public String getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
