package org.oladushek.entity.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class UpdatedEntity extends BaseEntity {
    private LocalDateTime created;
    private LocalDateTime updated;
}
