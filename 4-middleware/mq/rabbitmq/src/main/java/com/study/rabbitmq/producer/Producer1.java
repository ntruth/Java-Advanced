package com.study.rabbitmq.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName Producer1
 * @Description TODO
 * @Author ann
 * @Date 2021/7/7 15:14
 * @Version 1.0
 */
@Service
public class Producer1 {

    private RabbitTemplate rabbitTemplate;

    public Producer1(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage() {
        for (int i = 0; i < 10; i++) {
            // 开启ACK机制 确定消费者收到消息 才会发送下一条消息 效率低
//            rabbitTemplate.convertSendAndReceive("test-queue", "按抨击" + i);

            rabbitTemplate.convertAndSend("test-queue", "按抨击" + i);

        }
    }
}
