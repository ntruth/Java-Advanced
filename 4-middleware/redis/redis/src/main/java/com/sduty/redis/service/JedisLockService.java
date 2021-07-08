package com.sduty.redis.service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.params.SetParams;

import java.util.Collections;

/**
 * @ClassName JedisLockService
 * @Description TODO
 * @Author ann
 * @Date 2021/7/8 09:34
 * @Version 1.0
 */
public class JedisLockService {
    private static Jedis jedis;
    private static final String LOCK_SUCCESS = "OK";
    private static final Long RELEASE_SUCCESS = 1L;

    public static void close() {
        jedis.close();
    }

    /**
     * 尝试获取分布式锁
     *
     * @param lockKey    锁
     * @param clientUUID 请求标识
     * @param expireTime 超期时间,单位：毫秒
     * @return 是否获取成功
     */
    public static boolean tryAndGetDistributedLock(String lockKey, String clientUUID, int expireTime) {

        // 设置nx(不存在才创建)，px(该key过期时间)
        SetParams setParams = new SetParams().nx().px(expireTime);
        String result = jedis.set(lockKey, clientUUID, setParams);

        return LOCK_SUCCESS.equals(result);
    }

    /**
     * 释放分布式锁
     *
     * @param lockKey    锁
     * @param clientUUID 请求标识
     * @return 是否释放成功
     */
    public static boolean releaseDistributedLock(String lockKey, String clientUUID) {

        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(clientUUID));

        return RELEASE_SUCCESS.equals(result);
    }

    /**
     * 往名为channelName的频道中发送消息
     *
     * @param channelName channel名称
     * @param message     消息
     */
    public static void publish(String channelName, String message) {
        jedis.publish(channelName, message);
    }

    /**
     * 订阅channel
     *
     * @param jedisPubSub jedisPubSub实例
     * @param channelName channel名称
     */
    public static void subscribe(JedisPubSub jedisPubSub, String channelName) {
        jedis.subscribe(jedisPubSub, channelName);
    }
}
