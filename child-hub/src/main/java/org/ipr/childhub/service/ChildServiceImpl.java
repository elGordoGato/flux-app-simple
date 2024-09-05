package org.ipr.childhub.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ipr.childhub.client.GiftClient;
import org.ipr.childhub.data.entity.Child;
import org.ipr.childhub.data.repository.ChildRepository;
import org.ipr.childhub.model.ChildWithGifts;
import org.ipr.childhub.model.Gift;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChildServiceImpl implements ChildService {
    private final GiftClient giftClient;
    private final ChildRepository childRepository;

    @Override
    public Mono<ChildWithGifts> giveGiftsAndSave(Child child, boolean isAsync) {
        Mono<Child> childMono = (child.getId() != null)
                ? childRepository.findById(child.getId())
                : childRepository.save(child);
        return childMono
                .flatMap(savedChild -> {
                    Mono<Gift> giftMono = isAsync
                            ? giftClient.createGiftAsync(savedChild.getId())
                            : Mono.fromCallable(() -> giftClient.createGiftSync(savedChild.getId()));

                    return giftMono.map(gift -> new ChildWithGifts(savedChild, List.of(gift)));
                });
    }

    @Override
    public Mono<ChildWithGifts> getById(Long id, boolean isAsync) {
        return childRepository.findById(id)
                .flatMap(foundChild -> {
                    Flux<Gift> giftFlux = isAsync
                            ? giftClient.getGiftsAsync(foundChild.getId())
                            : Flux.fromIterable(giftClient.getGiftsSync(foundChild.getId()));
                    return giftFlux.collectList().map(gifts -> new ChildWithGifts(foundChild, gifts));
                });
    }

    @Override
    public Flux<ChildWithGifts> getAll(int limit, Direction direction, boolean isAsync) {
        Sort sort = Sort.by(direction, "created_at");
        Flux<Child> foundChild = childRepository.findAll(sort).take(limit).log();

        return foundChild.collectList().flatMapMany(childList -> {
            Map<Long, Child> childMap = childList.stream()
                    .collect(Collectors.toMap(Child::getId, Function.identity()));

            return isAsync
                    ? giftClient.getGiftsAsync(Flux.fromIterable(
                            childList.stream()
                                    .map(Child::getId)
                                    .collect(Collectors.toSet())))
                    .collectMultimap(Gift::getChildId)
                    .flatMapMany(giftMap -> Flux.fromIterable(
                            giftMap.keySet().stream()
                                    .map(childId -> new ChildWithGifts(
                                            childMap.get(childId),
                                            new ArrayList<>(giftMap.get(childId))))
                                    .collect(Collectors.toSet())))
                    : Flux.fromIterable(giftClient.getGiftsSync(
                            childList.stream()
                                    .map(Child::getId)
                                    .collect(Collectors.toList()))
                    .stream()
                    .collect(Collectors.groupingBy(Gift::getChildId))
                    .entrySet().stream()
                    .map(e -> new ChildWithGifts(
                            childMap.get(e.getKey()),
                            e.getValue()))
                    .collect(Collectors.toSet()));
        });

/*            return giftClient.getGiftsAsync(Flux.fromIterable(childList.stream().map(Child::getId).collect(Collectors.toSet())))
                    .collectList()
                    .flatMapMany(gifts -> {
                        gifts.forEach(g -> {
                            ChildWithGifts childWithGifts = childWithGiftsMap.getOrDefault(g.getChildId(), new ChildWithGifts(childMap.get(g.getChildId()), new ArrayList<>()));
                            childWithGifts.getGifts().add(g);
                            childWithGiftsMap.put(g.getChildId(), childWithGifts);
                        });
                        return Flux.fromIterable(childList.stream().map(c -> childWithGiftsMap.get(c.getId())).collect(Collectors.toSet()));
                    });
        });*/

        /*Flux<Long> childIds = Flux.from(foundChild).map(Child::getId);
        Flux<Gift> foundGifts = isAsync
                ? giftClient.getGiftsAsync(childIds)
                : childIds.collectList().map(giftClient::getGiftsSync).flatMapIterable(l -> l);
        return foundChild.flatMap(c -> foundGifts
                .filter(gift -> gift.getChildId().equals(c.getId()))
                .collectList()
                .map(giftList -> new ChildWithGifts(c, giftList)));*/
    }
}
