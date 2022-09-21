package com.lupo.interview.accessFintech.service;

import com.lupo.interview.accessFintech.pubsub.StockPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Service
public class LineStockReader implements StockReader{
private final StockPublisher stockPublisher;
    private static final Logger LOG = LoggerFactory.getLogger(LineStockReader.class);
    public LineStockReader(@Autowired StockPublisher stockPublisher) {
        this.stockPublisher = stockPublisher;
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
                stockPublisher.publish(line);
            }
        } catch (IOException e) {
            LOG.error("Failed to read stock data", e);
        }

    }
}
