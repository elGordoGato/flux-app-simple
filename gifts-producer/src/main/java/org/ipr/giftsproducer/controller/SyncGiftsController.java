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

import java.time.Instant;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/sync")
@RequiredArgsConstructor
public class SyncGiftsController {

    private final GiftService giftService;

    @PostMapping(value = "/gifts/{childId}")
    public Gift generateGiftSync(@PathVariable Long childId) {
        return giftService.createGiftSync(childId);
    }

    @PostMapping(value = "/gifts")
    public List<Gift> getGiftsSync(@RequestBody List<Long> childIds) {
        return giftService.getGiftsSync(childIds);
    }

    @GetMapping(value = "/gifts/{childId}")
    public List<Gift> getGiftsSync(@PathVariable Long childId) {
        Instant now = Instant.now();
        List<Gift> gifts = giftService.getGiftsSync(childId);
        log.info("Get Sync id {} duration - {}", childId, Instant.now().toEpochMilli()-now.toEpochMilli());
        return gifts;
    }
}