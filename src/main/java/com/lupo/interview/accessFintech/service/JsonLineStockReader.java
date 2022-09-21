package com.lupo.interview.accessFintech.service;

import com.lupo.interview.accessFintech.pubsub.StockPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

//@Service
public class JsonLineStockReader implements StockReader{
private final StockPublisher stockPublisher;
    private static final Logger LOG = LoggerFactory.getLogger(JsonLineStockReader.class);
    public JsonLineStockReader(@Autowired StockPublisher stockPublisher) {
        this.stockPublisher = stockPublisher;
    }

    @Override
    public void readData(InputStream inputStream) {
        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = br.readLine()) != null) {
                if(line.charAt(0) != '{') {
                    continue;
                }
                if(line.endsWith(",")) {
                    line = line.substring(0, line.length() - 1);
                }
                stockPublisher.publish(line);
            }
        } catch (IOException e) {
            LOG.error("Failed to read stock data", e);
        }

    }
}
