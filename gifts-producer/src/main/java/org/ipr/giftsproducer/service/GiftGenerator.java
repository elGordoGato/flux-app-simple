package org.ipr.giftsproducer.service;

import lombok.extern.slf4j.Slf4j;
import org.ipr.giftsproducer.data.entity.Gift;

import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Slf4j
public class GiftGenerator {
    private static final Random rand = new Random();
    private static final int priceBound = 32000;
    private static final List<String> toys = GiftParts.getToys();
    private static final List<String> toyAdjectives = GiftParts.getToyAdjectives();


    public static Gift generate(Long childId) {
        log.info("Generating gift for {}", childId);
        String toyName = "%s %s".formatted(
                toyAdjectives.get(rand.nextInt(toyAdjectives.size())),
                toys.get(rand.nextInt(toys.size())));

        return Gift.builder()
                .id(UUID.randomUUID().toString())
                .title(toyName)
                .price(rand.nextInt(priceBound))
                .childId(childId)
                .createdAt(Instant.now())
                .build();

    }
}
