package com.sduty.redis.controller;

import com.sduty.redis.service.JedisLockService;
import com.sduty.redis.utils.JedisLockUtil;
import com.sduty.redis.utils.JedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

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

    @Autowired
    private JedisLockUtil jedisLockUtil;

    private LongAdder stock;

    public LockController() {
        this.stock = new LongAdder();
        this.stock.add(10);
    }

    @GetMapping("jedis/lock")
    public String jedisLock() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);

        // key
        String lockName = UUID.randomUUID().toString().replace("-", "");

        // value
        String requestId = UUID.randomUUID().toString().replace("-", "");

        boolean lockState = jedisLockUtil.tryGetDistributedLock(jedis, lockName, requestId, 1000);
        System.out.println("抢占状态:" + lockState);
        // 成功获得锁
        try {
            if (lockState) {
                System.out.println("扣减库存之前剩余:" + stock.intValue());
                if (stock.intValue() <= 0) {
                    return "扣减失败，库存不足";
                }
                stock.decrement();
                System.out.println("扣减库存成功, 剩余:" + stock.toString() + "件");
            } else{
                System.out.println("扣减失败，未获取到分布式锁");
            }

            return "";
        } finally {
            //最后进行解锁操作，如果服务宕机，则到不了这一步
            Boolean releaseState = jedisLockUtil.releaseDistributedLock(jedis, lockName, requestId);
            System.out.println("释放了锁操作:" + releaseState);
        }
    }

//    @Autowired
//    private RedissonUtil redissonUtil;
//
//    @GetMapping("redisson/lock")
//    public String redissonLock() throws InterruptedException {
//
//        RLock lock = redisson.getLock("stock");
//
//        boolean b = lock.tryLock(10, TimeUnit.SECONDS);
//
//        if (b) {
//            String stock = String.valueOf(redissonUtil.getRBucket("stock"));
//            System.out.println("抢到分布式锁:" + stock);
//        }
//        // 业务逻辑
//        lock.unlock();
//
//        return "1111";
//    }


}
