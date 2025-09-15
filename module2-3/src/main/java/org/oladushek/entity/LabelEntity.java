package org.oladushek.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.oladushek.entity.base.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "labels")
public class LabelEntity extends BaseEntity {

    @Column(name= "name", nullable = false, unique = true, length = 100)
    private String name;

    @ManyToMany(mappedBy = "labels")
    private List<PostEntity> posts = new ArrayList<>();

    public LabelEntity(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "LabelEntity{" +
                "id='" +getId()+ '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
