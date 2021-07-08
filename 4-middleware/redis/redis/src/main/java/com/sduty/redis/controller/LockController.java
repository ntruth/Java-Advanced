package com.sduty.redis.controller;

import com.sduty.redis.service.JedisLockService;
import com.sduty.redis.utils.JedisUtil;
import jodd.util.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.misc.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPubSub;

import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.UUID;

/**
 * @ClassName JedisLockController
 * @Description TODO
 * @Author ann
 * @Date 2021/7/8 09:35
 * @Version 1.0
 */
@Slf4j
@RestController
public class LockController {

    @Autowired
    private JedisUtil jedisUtil;


    @GetMapping("jedis/lock")
    public String jedisLock() {
        // key
        String lockName = UUID.randomUUID().toString().replace("-", "");

        // value
        String requestId = UUID.randomUUID().toString().replace("-", "");

        boolean lockState = jedisUtil.tryGetDistributedLock(lockName, requestId, 1000);

        // 成功获得锁
        if (lockState) {
            try {
                Integer stock = Integer.parseInt(jedisUtil.get("stock", 0));
                if (stock <= 0) {
                    return "扣减失败，库存不足";
                }
                Long stock1 = jedisUtil.decr("stock");
                System.out.println("扣减库存成功, 剩余:" + stock1 + "件");
            } finally {
                //最后进行解锁操作，如果服务宕机，则到不了这一步
                jedisUtil.releaseDistributedLock(lockName, requestId);
            }

        } else{
            System.out.println("扣减失败，未获取到分布式锁");
        }

        return "执行到最后一步";
    }

}
