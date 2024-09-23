package org.ipr.childhub.client;

import lombok.extern.slf4j.Slf4j;
import org.ipr.childhub.model.Gift;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class GiftClient {
    private final String asyncBaseUrl;
    private final String syncBaseUrl;
    private final AsyncBaseClient asyncClient;
    private final SyncBaseClient syncClient;

    @Autowired
    public GiftClient(WebClient.Builder webClientBuilder,
                      @Value("${gift.baseUrl:http://localhost:8081}") String baseUrl) {
        this.asyncBaseUrl = baseUrl + "/async/gifts";
        this.syncBaseUrl = baseUrl + "/sync/gifts";
        this.asyncClient = new AsyncBaseClient(webClientBuilder, asyncBaseUrl);
        this.syncClient = new SyncBaseClient(new RestTemplate());
    }

    public Mono<Gift> createGiftAsync(Long childId) {
        return Mono.just(Objects.requireNonNull(asyncClient.post(asyncBaseUrl + "/" + childId, Mono.empty(), Long.class)
                .bodyToMono(Gift.class).block()));
    }

    public Flux<Gift> getGiftsAsync(Long childId) {
        return asyncClient.get(asyncBaseUrl + "/" + childId)
                .bodyToFlux(Gift.class);
    }


    public Flux<Gift> getGiftsAsync(Flux<Long> childWithGifts) {
        return asyncClient.post(asyncBaseUrl, childWithGifts, Long.class)
                .bodyToFlux(Gift.class);
    }

    public Gift createGiftSync(Long childId) {
        return syncClient.post(syncBaseUrl + "/" + childId, null, Gift.class).getBody();
    }

    public List<Gift> getGiftsSync(Long childId) {
        return Arrays.stream(Objects.requireNonNull(
                        syncClient.get(syncBaseUrl + "/" + childId, Gift[].class).getBody()))
                .toList();
    }

    public List<Gift> getGiftsSync(List<Long> childWithGifts) {
        return Arrays.stream(Objects.requireNonNull(
                        syncClient.post(syncBaseUrl, childWithGifts, Gift[].class).getBody()))
                .toList();
    }


    /*    public List<Gift> getGiftsSync(Flux<Long> childWithGifts) {
        return asyncClient.post(asyncBaseUrl, childWithGifts, Long.class)
                .bodyToFlux(Gift.class).collectList()
                .subscribeOn(Schedulers.boundedElastic())
                .block();
    }*/


/*    public List<Gift> getGiftsSync(Long childId) {
        return asyncClient.get(asyncBaseUrl + "/" + childId)
                .bodyToFlux(Gift.class).collectList()
                .subscribeOn(Schedulers.boundedElastic())
                .block();
    }*/

/*    public Gift createGiftSync(Long childId) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Gift> task = () -> asyncClient.post(asyncBaseUrl + "/" + childId, Mono.empty(), Long.class)
                .bodyToMono(Gift.class)
                .subscribeOn(Schedulers.boundedElastic())
                .block();

        Future<Gift> future = executor.submit(task);
        try {
            return future.get(); // This will block until the result is available
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null; // Handle the exception as needed
        } finally {
            executor.shutdown();
        }
    }*/
}
