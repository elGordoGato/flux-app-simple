package org.ipr.giftsproducer.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.ipr.giftsproducer.data.entity.Gift;
import org.ipr.giftsproducer.data.repository.GiftRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@Slf4j
@RequiredArgsConstructor
public class GiftServiceImpl implements GiftService {
    private static final short DELAY = 500;
    private final GiftRepository giftRepository;

    @Override
    public Mono<Gift> getGiftByChildId(Long childId) {
        return giftRepository.findByChildId(childId).delayElement(Duration.ofMillis(DELAY));
    }

    @SneakyThrows
    @Override
    public Gift getGiftByIdBlocking(Long childId) {
        return getGiftByChildId(childId).toFuture().get();
    }
}