package com.lupo.interview.accessFintech.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class StockClient {
    private static final Logger LOG = LoggerFactory.getLogger(StockClient.class);

    private final WebClient client;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");


    private final String stockUrl;

    public StockClient(@Autowired WebClient webClient, @Value("${server.stock.provider.url}") String stockUrl) {
        this.client = webClient;
        this.stockUrl = stockUrl;
    }

    @Scheduled(fixedRateString = "${server.scheduler.interval.millis}")
    public void pollData() {

        try {
            InputStream is = getResponseAsInputStream(client, stockUrl);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null) {
                LOG.info(line);
            }
        } catch (IOException | InterruptedException e) {
            LOG.error("Failed to retrieve stock from stock provider");
        }
        LOG.info("The time is now {}", dateFormat.format(new Date()));
    }
    public static InputStream getResponseAsInputStream(WebClient client, String url) throws IOException, InterruptedException {

        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(1024 * 10);
        pipedInputStream.connect(pipedOutputStream);

        Flux<DataBuffer> body = client.get()
                .uri(url)
                .exchangeToFlux(clientResponse -> {
                    return clientResponse.body(BodyExtractors.toDataBuffers());
                })
                .doOnError(error -> {
                    LOG.error("error occurred while reading body", error);
                })
                .doFinally(s -> {
                    try {
                        pipedOutputStream.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .doOnCancel(() -> {
                    LOG.error("Get request is cancelled");
                });

        DataBufferUtils.write(body, pipedOutputStream)
                .log("Writing to output buffer")
                .subscribe();
        return pipedInputStream;
    }
}