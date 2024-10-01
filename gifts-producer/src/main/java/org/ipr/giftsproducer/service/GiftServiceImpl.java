package org.ipr.giftsproducer.service;

import lombok.RequiredArgsConstructor;
import org.ipr.giftsproducer.data.entity.Gift;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class GiftServiceImpl implements GiftService {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);
    private final short delay = 500;


    @Override
    public Mono<Gift> createGift(Long childId) {
        Gift gift = GiftGenerator.generate(childId);
        return Mono.just(gift).delayElement(Duration.ofMillis(delay));
    }

    @Override
    public Gift createGiftSync(Long childId) {
        final Gift[] gift = new Gift[1];
        try {
            scheduler.schedule(() -> {
                gift[0] = GiftGenerator.generate(childId);
            }, delay, TimeUnit.MILLISECONDS).get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return gift[0];
    }
}