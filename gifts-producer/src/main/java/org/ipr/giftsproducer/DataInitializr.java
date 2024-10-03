package org.ipr.giftsproducer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ipr.giftsproducer.data.repository.GiftRepository;
import org.ipr.giftsproducer.service.GiftGenerator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@ConditionalOnProperty(value = "data.init.enabled", havingValue = "true")
@Component
@Slf4j
@RequiredArgsConstructor
class DataInitializr implements CommandLineRunner {
    private final GiftRepository giftRepository;

    @Override
    public void run(String[] args) {
        Flux.range(-50_000, 100_000)
                .doFirst(() -> log.info("start data initialization  ..."))
                .map(i -> GiftGenerator.generate(i.longValue()))
                .flatMap(giftRepository::save)
                .doAfterTerminate(() -> log.info("complete data initialization"))
                .subscribe();
    }

}
