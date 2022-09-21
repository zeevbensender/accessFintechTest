package com.lupo.interview.accessFintech.pubsub;

import com.lupo.interview.accessFintech.model.Stock;

public interface StockPublisher {
    void publish(Stock stock);
}
