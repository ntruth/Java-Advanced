package com.sduty.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName RedissonTests
 * @Description TODO
 * @Author ann
 * @Date 2021/7/8 10:35
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedissonTests {
    @Autowired
    private RedissonClient redissonClient;


    @Test
    public void test() {
        System.out.println(redissonClient);
    }
}
