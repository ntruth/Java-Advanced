package com.study.rabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName Consumer1
 * @Description 简单rabbitmq的消费者
 * @Author ann
 * @Date 2021/7/7 15:14
 * @Version 1.0
 */
@Component
public class Consumer1 {
    @RabbitHandler
    @RabbitListener(queues = "test-queue")
    public void process(Object object) {
        System.out.println("收到消息:" + object.toString());
    }
}
