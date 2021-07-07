//package com.study.rabbitmq.consumer;
//
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
///**
// * @ClassName Consumer2
// * @Description TODO
// * @Author ann
// * @Date 2021/7/7 15:36
// * @Version 1.0
// */
//@Component
//public class Consumer2 {
//    @RabbitListener(queues = "work-queue")
//    public void work1(Object object) {
//        System.out.println("消费者1接收到消息" + object);
//    }
//
//    @RabbitListener(queues = "work-queue")
//    public void work2(Object object) {
//        System.out.println("消费者2接收到消息" + object);
//    }
//
//}
