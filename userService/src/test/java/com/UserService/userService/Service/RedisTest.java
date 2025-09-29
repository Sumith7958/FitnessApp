package com.UserService.userService.Service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate; // Use specific types for better practice

    // 2. Remove @Disabled so the test actually runs
    @Test
    void testSendMail() {
        // The redisTemplate is now injected by Spring
        redisTemplate.opsForValue().set("email", "gmail@email.com");
        Object salary = redisTemplate.opsForValue().get("salary");
        int a = 1; // Test passes if no exception occurs
    }

}
