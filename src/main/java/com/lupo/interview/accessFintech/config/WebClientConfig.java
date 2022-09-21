package com.lupo.interview.accessFintech.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient getWebClient() {
        WebClient client = WebClient.builder()
                .baseUrl("https://s3.amazonaws.com")
//                .defaultCookie("cookieKey", "cookieValue")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .defaultUriVariables(Collections.singletonMap("url", "http://localhost:8080"))
                .build();
        return client;
    }
}
