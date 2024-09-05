package org.ipr.giftsproducer.data.repository;

import org.ipr.giftsproducer.data.entity.Gift;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface GiftSyncRepository extends MongoRepository<Gift, String> {
    List<Gift> findByChildId(Long childId);

}
