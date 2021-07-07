package com.sduty.redis;

import com.sduty.redis.utils.JedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JedisUtilTests {
    @Autowired
    private JedisUtil jedisUtil;

    @Test
    public void set() {
        jedisUtil.set("name", "张三", 0);

        System.out.println(jedisUtil.get("name", 0));
    }
}
