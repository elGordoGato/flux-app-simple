package org.ipr.childhub.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.ipr.childhub.data.entity.Child;
import reactor.core.publisher.Flux;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
public class ChildWithGifts {
    private Long id;
    private String name;
    private Byte age;
    private List<Gift> gifts;
    private Instant createdAt;
    private Instant updatedAt;

    public ChildWithGifts(Child child, List<Gift> gifts) {
        this.id = child.getId();
        this.name = child.getName();
        this.age = child.getAge();
        this.gifts = gifts;
        this.createdAt = child.getCreatedAt();
    }
}
