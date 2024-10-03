package org.ipr.giftsproducer.service;

import org.ipr.giftsproducer.data.entity.Gift;
import reactor.core.publisher.Mono;

public interface GiftService {
    Mono<Gift> getGiftByChildId(Long childId);

    Gift getGiftByIdBlocking(Long childId);
}
