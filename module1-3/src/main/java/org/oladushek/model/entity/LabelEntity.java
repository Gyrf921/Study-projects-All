package org.oladushek.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.oladushek.model.Status;
import org.oladushek.model.StatusEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LabelEntity extends StatusEntity {

    private String name;

    public LabelEntity(Long id, String name) {
        this.setId(id);
        this.name = name;
        this.setStatus(Status.ACTIVE);
    }
}
