package org.ipr.giftsproducer.service;

import lombok.RequiredArgsConstructor;
import org.ipr.giftsproducer.data.entity.Gift;
import org.ipr.giftsproducer.data.repository.GiftAsyncRepository;
import org.ipr.giftsproducer.data.repository.GiftSyncRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GiftServiceImpl implements GiftService {
    private final GiftAsyncRepository giftAsyncRepository;
    private final GiftSyncRepository giftSyncRepository;

    @Override
    public Mono<Gift> createGift(Long childId) {
        return giftAsyncRepository.save(GiftGenerator.generate(childId));
    }

    @Override
    public Flux<Gift> getGifts(Flux<Long> childIds) {
        return childIds.map(s -> {
                    System.out.print(s);
                    return s;
                })
                .flatMap(giftAsyncRepository::findAllByChildIdIs);
    }

    @Override
    public Flux<Gift> getGifts(Long childId) {
        return giftAsyncRepository.findAllByChildIdIs(childId).delaySubscription(Duration.ofMillis(100));
    }

    @Override
    public Gift createGiftSync(Long childId) {
        return giftSyncRepository.save(GiftGenerator.generate(childId));
    }

    @Override
    public List<Gift> getGiftsSync(Long childId) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return giftSyncRepository.findByChildId(childId);
    }

    @Override
    public List<Gift> getGiftsSync(List<Long> childIds) {
        return List.of();
    }
}