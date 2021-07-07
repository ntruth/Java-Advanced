package com.study.rabbitmq.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName Producer3
 * @Description Fanout模式
 * @Author ann
 * @Date 2021/7/7 15:51
 * @Version 1.0
 */
@Service
public class Producer3 {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage( ) {
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("fanout_exchange", "", "按抨:" + i);
//            rabbitTemplate.convertSendAndReceive("fanout_exchange", "", "按抨:" + i);
        }
    }
}
