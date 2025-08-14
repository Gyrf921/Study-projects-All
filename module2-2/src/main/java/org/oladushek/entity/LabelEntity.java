package org.oladushek.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.oladushek.entity.base.BaseEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class LabelEntity extends BaseEntity {

    private String name;

    public LabelEntity(Long id, String name) {
        this.setId(id);
        this.name = name;
    }
}
