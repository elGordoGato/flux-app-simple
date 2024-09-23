package org.ipr.childhub.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
public class SyncBaseClient {
    private final RestTemplate restTemplate;


    <T> ResponseEntity<T> post(String uri, Object requestBody, Class<T> responseType) {
        var response = restTemplate.postForEntity(uri, requestBody, responseType);
        log.info("Sent sync POST request to {} \nReceived response class {}", uri, response.getBody());
        return response;
    }

    <T> ResponseEntity<T> get(String uri, Class<T> responseType) {
        var response = restTemplate.getForEntity(uri, responseType);
        log.info("Sent sync GET request to {} \nReceived response class {}", uri, response.getBody());
        return response;
    }
}
