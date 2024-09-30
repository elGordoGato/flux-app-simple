package org.ipr.childhub.data.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.List;
import java.util.Random;

@Table("children")
@Data
@Accessors(chain = true)
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Child info")
public class Child {
    @Id
    @Schema(hidden = true)
    private Long id;

    @Schema(example = "Bob")
    private String name;

    @Schema(example = "9", type = "integer")
    private byte age;

    @CreatedDate
    @Schema(hidden = true)
    private Instant createdAt;
}
