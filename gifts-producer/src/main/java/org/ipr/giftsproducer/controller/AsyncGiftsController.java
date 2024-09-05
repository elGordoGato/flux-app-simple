package org.ipr.giftsproducer.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ipr.giftsproducer.data.entity.Gift;
import org.ipr.giftsproducer.service.GiftService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Slf4j
@RestController
@RequestMapping("/async")
@RequiredArgsConstructor
public class AsyncGiftsController {
    private final GiftService giftService;

    @GetMapping("/gifts/health")
    public Mono<String> healthcheck(){
        return Mono.just("OK");
    }

    @PostMapping(value = "/gifts/{childId}")
    public Mono<Gift> generateGiftAsync(@PathVariable Long childId) {
        return giftService.createGift(childId);
    }

    @PostMapping(value = "/gifts")
    public Flux<Gift> getGiftsAsync(@RequestBody Flux<Long> childIds) {
        return giftService.getGifts(childIds);
    }

    @GetMapping(value = "/gifts/{childId}")
    public Flux<Gift> getGiftsAsync(@PathVariable Long childId) {
        Instant mow = Instant.now();

        return giftService.getGifts(childId).doAfterTerminate(() -> log.info("Diration call for id: {} - {} ms",
                childId, Instant.now().toEpochMilli() - mow.toEpochMilli()));
    }
}
