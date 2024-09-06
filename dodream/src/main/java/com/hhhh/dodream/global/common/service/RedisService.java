package com.hhhh.dodream.global.common.service;

import com.hhhh.dodream.global.common.enums.RedisKeyPrefixEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final StringRedisTemplate redisTemplate;

    public void deleteKey(RedisKeyPrefixEnum keyPrefix, Long id) {
        redisTemplate.delete(keyPrefix.getDescription() + id);
    }
}
