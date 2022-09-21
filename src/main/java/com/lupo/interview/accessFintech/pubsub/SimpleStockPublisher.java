package com.lupo.interview.accessFintech.pubsub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Queue;

@Service
public class SimpleStockPublisher implements StockPublisher{
    private final JmsTemplate jmsTemplate;
    private final Queue queue;

    public SimpleStockPublisher(@Autowired JmsTemplate jmsTemplate, @Autowired Queue queue) {
        this.jmsTemplate = jmsTemplate;
        this.queue = queue;
    }

    private static final Logger LOG = LoggerFactory.getLogger(SimpleStockPublisher.class);
    @Override
    public void publish(String stock) {
        jmsTemplate.convertAndSend(queue, stock);
    }
}
