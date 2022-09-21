package com.lupo.interview.accessFintech.pubsub;

import com.lupo.interview.accessFintech.model.Stock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SimpleStockPublisher implements StockPublisher{
    private static final Logger LOG = LoggerFactory.getLogger(SimpleStockPublisher.class);
    @Override
    public void publish(Stock stock) {
        LOG.info(stock.toString());
    }
}
