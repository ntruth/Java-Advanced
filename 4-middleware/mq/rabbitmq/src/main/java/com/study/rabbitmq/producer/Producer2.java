package com.study.rabbitmq.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName Producer2
 * @Description Work模式 一个生产者 多个消费者
 * @Author ann
 * @Date 2021/7/7 15:35
 * @Version 1.0
 */
@Service
public class Producer2 {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage() {
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("work-queue", "张三:" + i);
        }
    }
}
