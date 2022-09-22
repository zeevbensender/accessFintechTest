package com.lupo.interview.accessFintech.pubsub;

import java.io.InputStream;

public interface StockPublisher {
    void readData(InputStream inputStream);
}
