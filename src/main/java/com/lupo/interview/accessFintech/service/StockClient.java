package com.lupo.interview.accessFintech.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class StockClient {
    private static final Logger LOG = LoggerFactory.getLogger(StockClient.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRateString = "${server.scheduler.interval.millis}")
    public void pollData() {
        LOG.info("The time is now {}", dateFormat.format(new Date()));
    }
}
