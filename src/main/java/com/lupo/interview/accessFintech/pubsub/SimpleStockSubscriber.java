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
        if(message.charAt(0) == '{') {
            dataAccessService.saveOrUpdateStock(parseJson(message));
        }else {
            String[] values = message.split(",");
            if(values.length > 1) {
                dataAccessService.saveOrUpdateStock(parseCsv(values));
            }
        }
    }

    private Stock parseCsv(String[] values) {
        return new Stock(values[0], Double.valueOf(values[2].strip()));
    }
    private Stock parseJson(String message) {
        if(message.endsWith(",")) {
            message = message.substring(0, message.length() - 1);
        }
        return gson.fromJson(message, Stock.class);
    }
}
