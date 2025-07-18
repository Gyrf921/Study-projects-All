package org.oladushek.model;

import lombok.Data;
import lombok.NonNull;
import lombok.ToString;

@Data
public class Label extends BaseEntity{

    private String name;

    public Label(Long id, @NonNull String name) {
        this.setId(id);
        this.name = name;
        this.setStatus(Status.ACTIVE);
    }

    public Label(@NonNull String name) {
        this.setId(-1L);
        this.name = name;
        this.setStatus(Status.ACTIVE);
    }
}
