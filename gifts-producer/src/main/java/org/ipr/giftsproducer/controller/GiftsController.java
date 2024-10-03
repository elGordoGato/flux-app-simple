package org.ipr.giftsproducer.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ipr.giftsproducer.data.entity.Gift;
import org.ipr.giftsproducer.service.GiftService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
public class GiftsController {
    private final GiftService giftService;

    @GetMapping("/gifts/health")
    public Mono<String> healthcheck() {
        return Mono.just("OK");
    }

    @PostMapping(value = "/gifts/{childId}")
    public Mono<Gift> generateGiftAsync(@PathVariable Long childId) {
        return giftService.getGiftByChildId(childId);
    }

    @PostMapping(value = "/gifts/sync/{childId}")
    public Gift generateGiftSync(@PathVariable Long childId) {
        return giftService.getGiftByIdBlocking(childId);
    }
}
