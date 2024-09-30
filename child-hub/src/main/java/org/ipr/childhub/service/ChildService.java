package org.ipr.childhub.service;

import org.ipr.childhub.model.ChildWithGift;
import reactor.core.publisher.Mono;

public interface ChildService {
    Mono<ChildWithGift> getByIdSync(Long id);

    Mono<ChildWithGift> getByIdAsync(Long id, boolean isAsync);
}
