package com.lupo.interview.accessFintech.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class StockFileReader {
    private static final Logger LOG = LoggerFactory.getLogger(StockFileReader.class);

    private final StockReader stockReader;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private final String[] filesPath;

    private ExecutorService executorService;

    public StockFileReader(@Autowired StockReader stockReader,
                           @Value("${server.stock.file.path}") String filePath) {
        this.stockReader = stockReader;
        this.filesPath = filePath.split(",");
        executorService = new ThreadPoolExecutor(
                this.filesPath.length,
                this.filesPath.length, 0l, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
    }

    @Scheduled(fixedRateString = "${server.scheduler.interval.millis}")
    public void pollData() {
        for(String path : filesPath) {
            executorService.execute(() -> readStocks(path));
        }
    }

    private void readStocks(String filePath) {
        try(FileInputStream is = new FileInputStream(filePath)){
            stockReader.readData(is);
            LOG.info("Reading data from file {} ({})", filePath, dateFormat.format(new Date()));
        }catch (IOException e){
            LOG.error("Failed to load stock data from {} file", filePath);
        }
    }
}
