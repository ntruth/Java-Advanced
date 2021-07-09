package com.sduty.redis.utils;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.Collections;

/**
 * @ClassName JedisLockUtil
 * @Description TODO
 * @Author ann
 * @Date 2021/7/9 10:07
 * @Version 1.0
 */
@Component
public class JedisLockUtil {

    private static final Long RELEASE_SUCCESS = 1L;

    private static final String LOCK_SUCCESS = "OK";

    // 这种方案存在问题:
    // 由于这里设置的过期时间是固定的，因此可能存在一种情况当客户端业务没有执行完但是key过期释放了，
    // 因此这里设置的过期时间不能过短，要和业务常规执行耗时结合。
    // 但是较好的方案是开启一个新线程不断地去检查当前key是否存在，存在则对该key进行续时操作。
    // 后面讲基于redisson实现的分布式锁时会讲到在redisson中使用了watchdog来不断为key续时解决了这一问题。

    /**
     * 尝试获取分布式锁
     *
     * @param jedis      Redis客户端
     * @param lockKey    锁
     * @param requestId  请求标识
     * @param expireTime 超期时间（毫秒）
     * @return 是否获取成功
     */
    public boolean tryGetDistributedLock(Jedis jedis, String lockKey, String requestId, int expireTime) {
        // 设置nx(不存在才创建)，px(该key过期时间)
        SetParams setParams = new SetParams().nx().px(expireTime);

        String result = jedis.set(lockKey, requestId, setParams);

        // 是否获取成功
        return LOCK_SUCCESS.equals(result);
    }

    /**
     * 释放分布式锁
     *
     * @param jedis     Redis客户端
     * @param lockKey   锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public boolean releaseDistributedLock(Jedis jedis, String lockKey, String requestId) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
        // 此处包含了key被删除了或者value被修改了的两种情况
        return RELEASE_SUCCESS.equals(result);
    }
}
