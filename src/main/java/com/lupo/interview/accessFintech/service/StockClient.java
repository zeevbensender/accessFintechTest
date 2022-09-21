package com.lupo.interview.accessFintech.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class StockClient {
    private static final Logger LOG = LoggerFactory.getLogger(StockClient.class);

    private final WebClient client;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public StockClient(@Autowired WebClient webClient) {
        this.client = webClient;
    }

    @Scheduled(fixedRateString = "${server.scheduler.interval.millis}")
    public void pollData() {

        WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = client.method(HttpMethod.GET);
        WebClient.RequestBodySpec requestBodySpec = uriSpec.uri("/test-data-samples/stocks.json");
        Mono<String> response = requestBodySpec.retrieve()
                .bodyToMono(String.class);
        LOG.info(response.block());
        LOG.info("The time is now {}", dateFormat.format(new Date()));
    }
}
