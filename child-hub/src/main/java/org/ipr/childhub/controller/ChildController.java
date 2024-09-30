package org.ipr.childhub.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ipr.childhub.model.ChildWithGift;
import org.ipr.childhub.service.ChildService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Slf4j
@RestController
@RequestMapping("/children")
@RequiredArgsConstructor
public class ChildController {
    private final ChildService childService;

    @GetMapping("/health")
    public Mono<String> healthcheck() {
        return Mono.just("OK");
    }

    @GetMapping("/async/{id}")
    public Mono<ChildWithGift> getByIdAsync(@PathVariable long id,
                                            @RequestParam boolean isBlocking) {
        Instant now = Instant.now();
        return childService.getByIdAsync(id, isBlocking)
                .doAfterTerminate(() -> log.info("GET id: {} duration {}ms",
                        id, Instant.now().toEpochMilli() - now.toEpochMilli()));
    }

    @GetMapping("/sync/{id}")
    public Mono<ChildWithGift> getByIdSync(@PathVariable long id) {
        Instant now = Instant.now();
        return childService.getByIdSync(id)
                .doAfterTerminate(() -> log.info("GET id: {} duration {}ms",
                        id, Instant.now().toEpochMilli() - now.toEpochMilli()));
    }


}
