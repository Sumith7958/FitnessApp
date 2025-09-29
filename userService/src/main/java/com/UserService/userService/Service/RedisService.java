package com.UserService.userService.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;


@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public <T> T get(String key) {
        try {
            Object cachedObject = redisTemplate.opsForValue().get(key);

            return (T) cachedObject;

        } catch (Exception e) {
            log.error("Exception during GET from Redis: ", e);
            return null;
        }
    }

    public void set(String key, Object o, Long ttl) {
        try {
            redisTemplate.opsForValue().set(key, o, ttl, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Exception during SET to Redis: ", e);
        }
    }
}