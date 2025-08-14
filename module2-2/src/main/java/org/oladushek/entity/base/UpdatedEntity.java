package org.oladushek.entity.base;

import lombok.*;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class UpdatedEntity extends BaseEntity {
    private LocalDateTime created;
    private LocalDateTime updated;
}
