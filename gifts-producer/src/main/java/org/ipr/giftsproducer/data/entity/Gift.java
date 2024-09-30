package org.ipr.giftsproducer.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Gift {
    private String id;

    private Long childId;

    private String title;

    private Integer price;
    private Instant createdAt;
}
