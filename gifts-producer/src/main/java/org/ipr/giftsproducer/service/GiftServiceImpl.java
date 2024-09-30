package org.ipr.giftsproducer.service;

import lombok.RequiredArgsConstructor;
import org.ipr.giftsproducer.data.entity.Gift;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class GiftServiceImpl implements GiftService {

    @Override
    public Mono<Gift> createGift(Long childId) {
        Gift gift = GiftGenerator.generate(childId);
        return Mono.just(gift).log().delayElement(Duration.ofMillis(500));
    }

    @Override
    public Gift createGiftSync(Long childId) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return GiftGenerator.generate(childId);
    }
}