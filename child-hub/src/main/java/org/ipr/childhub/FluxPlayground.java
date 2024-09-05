package org.ipr.childhub;

import lombok.Getter;
import lombok.Setter;
import reactor.core.publisher.Flux;

public class FluxPlayground {
    public static void main(String[] args) {
        Flux<Child> childs = getChilds().log(); // Assume this is your initial Flux<Child>
        Flux<Integer> childIds = childs.map(Child::getId);

        Flux<Gift> gifts = getGiftsFromService(childIds);

        Flux<Child> updatedChilds = childs.flatMap(child ->
                gifts.filter(gift -> gift.getChildId().equals(child.getId()))
                        .next()
                        .map(gift -> {
                            child.setGift(gift);
                            return child;
                        })
        );

        updatedChilds.subscribe(System.out::println);
    }

    private static Flux<Child> getChilds() {
        // Mock implementation
        return Flux.just(new Child(1), new Child(2), new Child(3));
    }

    private static Flux<Gift> getGiftsFromService(Flux<Integer> childIds) {
        System.out.println("Called service");
        childIds.subscribe(System.out::println);
        return childIds.map(c -> new Gift(c, "Toy"));
    }
}

@Getter
class Child {
    private final Integer id;
    @Setter
    private Gift gift;

    public Child(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Child{id=" + id + ", gift=" + gift + '}';
    }
}

class Gift {
    private Integer childId;
    private String name;

    public Gift(Integer childId, String name) {
        this.childId = childId;
        this.name = name;
    }

    public Integer getChildId() {
        return childId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Gift{childId=" + childId + ", name='" + name + '\'' + '}';
    }
}
