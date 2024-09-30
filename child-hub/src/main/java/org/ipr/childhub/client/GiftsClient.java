package org.ipr.childhub.client;

import lombok.extern.slf4j.Slf4j;
import org.ipr.childhub.model.Gift;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
@Qualifier("WebClient")
public class GiftsClient {
    private final WebClient webClient;
    private final RestTemplate restTemplate;
    private final String baseUrl;

    public GiftsClient(@Value("${gift.baseUrl:http://localhost:8081/gifts}") String baseUrl) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
        this.restTemplate = new RestTemplate();
        this.baseUrl = baseUrl;
    }

    public Mono<Gift> getByIdAsync(Long id) {
        log.info("Sending request to GiftsWebClient#getByIdAsync with id {}", id);
        return webClient.post().uri("/" + id).retrieve().bodyToMono(Gift.class);
    }

    public Gift getByIdSync(Long id) {
        log.info("Sending request to GiftsWebClient#getByIdSync with id {}", id);
        return restTemplate.postForEntity(baseUrl + "/sync/" + id, null, Gift.class).getBody();
    }
}
