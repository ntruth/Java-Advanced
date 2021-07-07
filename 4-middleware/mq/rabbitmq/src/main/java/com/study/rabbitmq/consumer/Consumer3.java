//package com.study.rabbitmq.consumer;
//
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
///**
// * @ClassName Consumer3
// * @Description TODO
// * @Author ann
// * @Date 2021/7/7 15:51
// * @Version 1.0
// */
//@Component
//public class Consumer3 {
//
//    @RabbitListener(queues = "fanout_queue_one")
//    public void reciveOne(Object object) {
//        System.out.println("fanout_queue_one接收到消息，消费者1: " + object);
//    }
//
//    @RabbitListener(queues = "fanout_queue_one")
//    public void reciveTwo(Object object) {
//        System.out.println("fanout_queue_one接收到消息，消费者2: " + object );
//    }
//
//    //-------------一个队列绑定两个消费者 --------------------------------
//    @RabbitListener(queues = "fanout_queue_two")
//    public void consumerTwo(Object object) {
//        System.out.println("fanout_queue_two队列 消费者1：收到消息---" + object);
//    }
//
//    @RabbitListener(queues = "fanout_queue_two")
//    public void consumerTwo2(Object object) {
//        System.out.println("fanout_queue_two队列 消费者2：收到消息---" + object);
//    }
//
//}
