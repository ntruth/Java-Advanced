package com.study.rabbitmq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName RabbitConfig
 * @Description TODO
 * @Author ann
 * @Date 2021/7/7 15:14
 * @Version 1.0
 */
@Configuration
public class RabbitConfig {
    @Bean
    public Queue queue() {
        return new Queue("test-queue");
//        return new Queue("work-queue");
    }
}
