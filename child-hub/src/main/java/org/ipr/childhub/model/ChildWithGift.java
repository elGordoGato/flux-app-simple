package org.ipr.childhub.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.ipr.childhub.data.entity.Child;

import java.time.Instant;

@Data
@AllArgsConstructor
public class ChildWithGift {
    private Long id;
    private String name;
    private Byte age;
    private Gift gift;
    private Instant createdAt;

    public ChildWithGift(Child child, Gift gift) {
        this.id = child.getId();
        this.name = child.getName();
        this.age = child.getAge();
        this.gift = gift;
        this.createdAt = child.getCreatedAt();
    }
}
