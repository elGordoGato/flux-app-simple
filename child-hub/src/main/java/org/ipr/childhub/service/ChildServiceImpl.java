package org.ipr.childhub.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.ipr.childhub.client.GiftsClient;
import org.ipr.childhub.data.entity.Child;
import org.ipr.childhub.data.repository.ChildRepository;
import org.ipr.childhub.model.ChildWithGift;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChildServiceImpl implements ChildService {
    private final GiftsClient giftClient;
    private final ChildRepository childRepository;
    private final Scheduler blockingScheduler = Schedulers.newBoundedElastic(
            10, 100, "blocking");

    @Override
    public Mono<ChildWithGift> getByIdSync(Long id) {
        Mono<Child> childMono = childRepository.findById(id);
        return childMono.map(child -> (new ChildWithGift(child, giftClient.getByIdSync(id))));
    }

    @Override
    public Mono<ChildWithGift> getByIdAsync(Long id, boolean isBlocking) {
        return isBlocking
                ? getByIdWithBlocking(id)
                : getByIdWithoutBlocking(id);
    }

    private Mono<ChildWithGift> getByIdWithBlocking(Long id) {
        return childRepository.findById(id)
                .publishOn(blockingScheduler)
                .map(child ->
                        new ChildWithGift(child,
                                giftClient.getByIdAsync(id).block()));
    }

    @SneakyThrows
    private Mono<ChildWithGift> getByIdWithoutBlocking(Long id) {
        return childRepository.findById(id).log()
                .flatMap(child -> giftClient.getByIdAsync(id)
                        .map(gift -> new ChildWithGift(child, gift)));
    }
}
