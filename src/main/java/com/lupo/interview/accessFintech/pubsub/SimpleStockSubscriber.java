package com.lupo.interview.accessFintech.pubsub;

import com.google.gson.Gson;
import com.lupo.interview.accessFintech.model.DataAccessService;
import com.lupo.interview.accessFintech.model.Stock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class SimpleStockSubscriber implements StockSubscriber{
    private static final Logger LOG = LoggerFactory.getLogger(SimpleStockSubscriber.class);
    private Gson gson = new Gson();
    private final DataAccessService dataAccessService;

    public SimpleStockSubscriber(@Autowired DataAccessService dataAccessService) {
        this.dataAccessService = dataAccessService;
    }


    @Override
    @JmsListener(destination = "${server.stock.queue.name}")
    public void onMessage(String message) {
        Stock stock = gson.fromJson(message, Stock.class);
        dataAccessService.saveOrUpdateStock(stock);
    }
}
