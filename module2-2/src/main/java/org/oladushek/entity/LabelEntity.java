package org.oladushek.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.oladushek.entity.base.BaseEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LabelEntity extends BaseEntity {

    private String name;

    public LabelEntity(Long id, String name) {
        this.setId(id);
        this.name = name;
    }
}
