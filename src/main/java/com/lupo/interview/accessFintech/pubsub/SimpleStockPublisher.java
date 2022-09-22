package com.lupo.interview.accessFintech.pubsub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Queue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Service
public class SimpleStockPublisher implements StockPublisher{
    private static final Logger LOG = LoggerFactory.getLogger(SimpleStockPublisher.class);
    private final JmsTemplate jmsTemplate;
    private final Queue queue;
    public SimpleStockPublisher(@Autowired JmsTemplate jmsTemplate, @Autowired Queue queue) {
        this.jmsTemplate = jmsTemplate;
        this.queue = queue;
    }

    @Override
    public void readData(InputStream inputStream) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                if(first) {
                    first = false;
                    continue;
                }
                jmsTemplate.convertAndSend(queue, line);
            }
        } catch (IOException e) {
            LOG.error("Failed to read stock data", e);
        }

    }
}
