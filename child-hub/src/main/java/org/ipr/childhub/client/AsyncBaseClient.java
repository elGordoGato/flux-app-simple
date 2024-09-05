package org.ipr.childhub.client;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
public class AsyncBaseClient {
    private final WebClient asyncClient;

    public AsyncBaseClient(WebClient.Builder webClientBuilder, String baseUrl) {
        this.asyncClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    <T, P extends Publisher<T>> WebClient.ResponseSpec post(String uri, P publisher, Class<T> inputType) {
        var request = asyncClient.post().uri(uri);
        var response = (publisher == null || inputType == null) ?
                request.retrieve() :
                request.body(BodyInserters.fromPublisher(publisher, inputType))
                        .retrieve();
        log.info("Sent async POST request to {}\nResponse class: {}", uri, response.getClass());
        return response;
    }

    WebClient.ResponseSpec get(String uri) {
        var response = asyncClient.get()
                .uri(uri)
                .retrieve();
        log.info("Sent async GET request to {}\nReceived response class: {}", uri, response.getClass());
        return response;
    }
}
