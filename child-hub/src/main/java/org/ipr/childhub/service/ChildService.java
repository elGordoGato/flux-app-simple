package org.ipr.childhub.service;

import org.ipr.childhub.data.entity.Child;
import org.ipr.childhub.model.ChildWithGifts;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ChildService {
    Mono<ChildWithGifts> giveGiftsAndSave(Child child, boolean isAsync);

    Mono<ChildWithGifts> getById(Long id, boolean isAsync);

    Flux<ChildWithGifts> getAll(int limit, Sort.Direction direction, boolean isAsync);
}
