package com.lupo.interview.accessFintech.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

import javax.jms.Queue;

@EnableJms
@Configuration
public class ActiveMQConfig {
    private final String queName;

    public ActiveMQConfig(@Value("${server.stock.queue.name}") String queName) {
        this.queName = queName;
    }

    @Bean
    public Queue createQueue() {
        return new ActiveMQQueue(queName);
    }
}
