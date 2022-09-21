package com.lupo.interview.accessFintech.pubsub;

public interface StockSubscriber {
    void onMessage(String message);
}
