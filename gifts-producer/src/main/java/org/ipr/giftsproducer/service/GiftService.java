package org.ipr.giftsproducer.service;

import org.ipr.giftsproducer.data.entity.Gift;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface GiftService {
    Mono<Gift> createGift(Long childId);

    Flux<Gift> getGifts(Flux<Long> childIds);

    Flux<Gift> getGifts(Long childId);

    Gift createGiftSync(Long childId);

    List<Gift> getGiftsSync(Long childIds);

    List<Gift> getGiftsSync(List<Long> childIds);
}
