package org.ipr.giftsproducer.data.repository;

import org.ipr.giftsproducer.data.entity.Gift;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface GiftAsyncRepository extends ReactiveMongoRepository<Gift, Long> {
    Flux<Gift> findAllByChildIdIs(Long childId);
}
