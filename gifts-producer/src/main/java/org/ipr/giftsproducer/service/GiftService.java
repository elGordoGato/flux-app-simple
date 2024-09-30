package org.ipr.giftsproducer.service;

import org.ipr.giftsproducer.data.entity.Gift;
import reactor.core.publisher.Mono;

public interface GiftService {
    Mono<Gift> createGift(Long childId);

    Gift createGiftSync(Long childId);
}
