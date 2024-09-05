package org.ipr.childhub.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ipr.childhub.data.entity.Child;
import org.ipr.childhub.model.ChildWithGifts;
import org.ipr.childhub.service.ChildService;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
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

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ChildWithGifts> save(
            @RequestParam(defaultValue = "true") boolean isAsync,
            @RequestBody Child child) {
        Instant now = Instant.now();
        return childService.giveGiftsAndSave(child, isAsync)
                .doAfterTerminate(() -> log.info("duration {}", Instant.now().toEpochMilli() - now.toEpochMilli()));
    }

    @GetMapping
    public Flux<ChildWithGifts> get(
            @RequestParam(defaultValue = "100") int limit,
            @RequestParam(defaultValue = "ASC") Direction direction,
            @RequestParam(defaultValue = "true") boolean isAsync) {
        Instant now = Instant.now();
        return childService.getAll(limit, direction, isAsync)
                .doAfterTerminate(() -> log.info("duration {}", now.toEpochMilli() - now.toEpochMilli()));
    }

    @GetMapping("/{id}")
    public Mono<ChildWithGifts> getById(@PathVariable long id,
                                        @RequestParam(defaultValue = "true") boolean isAsync) {
        Instant now = Instant.now();
        return childService.getById(id, isAsync)
                .doAfterTerminate(() -> log.info("duration {}", Instant.now().toEpochMilli() - now.toEpochMilli()));
    }


}
