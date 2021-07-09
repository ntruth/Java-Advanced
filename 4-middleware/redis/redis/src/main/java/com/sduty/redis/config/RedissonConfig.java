//package com.sduty.redis.config;
//
//import org.redisson.Redisson;
//import org.redisson.api.RedissonClient;
//import org.redisson.config.Config;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.io.IOException;
//
///**
// * @ClassName RedissonConfig
// * @Description TODO
// * @Author ann
// * @Date 2021/7/8 10:34
// * @Version 1.0
// */
//@Configuration
//public class RedissonConfig {
//    /**
//     * 对 Redisson 的使用都是通过 RedissonClient 对象
//     * @return
//     * @throws IOException
//     */
//    @Bean(destroyMethod="shutdown") // 服务停止后调用 shutdown 方法。
//    public RedissonClient redisson() throws IOException {
//        // 1.创建配置
//        Config config = new Config();
//        // 集群模式
//        // config.useClusterServers().addNodeAddress("127.0.0.1:7004", "127.0.0.1:7001");
//        // 2.根据 Config 创建出 RedissonClient 示例。
//        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
//        config.setLockWatchdogTimeout(10);
//        return Redisson.create(config);
//    }
//}
