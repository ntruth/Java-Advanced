package com.study.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName FanoutExchangeConfig
 * @Description Fanout模式
 * @Author ann
 * @Date 2021/7/7 15:46
 * @Version 1.0
 */
@Configuration
public class FanoutExchangeConfig {
    @Bean
    public Queue fanoutQueueOne() {
        return new Queue("fanout_queue_one");
    }

    @Bean
    public Queue fanoutQueueTwo() {
        return new Queue("fanout_queue_two");
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanout_exchange");
    }

    @Bean
    public Binding bindingFanoutExchangeA(Queue fanoutQueueOne, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutQueueOne).to(fanoutExchange);
    }

    @Bean
    public Binding bindingFanoutExchangeB(Queue fanoutQueueTwo, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutQueueTwo).to(fanoutExchange);
    }

}
