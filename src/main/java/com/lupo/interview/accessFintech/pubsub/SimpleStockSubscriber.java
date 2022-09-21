package com.lupo.interview.accessFintech.pubsub;

import com.google.gson.Gson;
import com.lupo.interview.accessFintech.model.Stock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class SimpleStockSubscriber implements StockSubscriber{
    private static final Logger LOG = LoggerFactory.getLogger(SimpleStockSubscriber.class);
    private Gson gson = new Gson();
    @Override
    @JmsListener(destination = "${server.stock.queue.name}")
    public void onMessage(String message) {
        Stock stock = gson.fromJson(message, Stock.class);
        LOG.info("Stock message: {}", stock);
    }
}
