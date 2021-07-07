package com.study.rabbitmq.controller;

import com.study.rabbitmq.producer.Producer1;
import com.study.rabbitmq.producer.Producer2;
import com.study.rabbitmq.producer.Producer3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName RabbitController
 * @Description TODO
 * @Author ann
 * @Date 2021/7/7 15:18
 * @Version 1.0
 */
@RestController
public class RabbitController {
    @Autowired
    private Producer1 producer1;

    @Autowired
    private Producer2 producer2;

    @Autowired
    private Producer3 producer3;

    @GetMapping("send")
    public void send() {
        producer1.sendMessage();
//        producer2.sendMessage();
//        producer3.sendMessage();
    }


}
