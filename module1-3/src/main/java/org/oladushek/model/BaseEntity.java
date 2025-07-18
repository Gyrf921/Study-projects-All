package org.oladushek.model;

import lombok.Data;
import lombok.ToString;

@Data
public class BaseEntity {
    private Long id;

    @ToString.Exclude
    private Status status;
}
