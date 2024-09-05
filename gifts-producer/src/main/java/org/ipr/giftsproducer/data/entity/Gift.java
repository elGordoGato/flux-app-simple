package org.ipr.giftsproducer.data.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Gift {

    @Id
    private String id;

    private Long childId;

    private String title;

    private Integer price;

    @CreatedDate
    private Instant createdAt;
}
