package org.oladushek.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.oladushek.entity.base.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class WriterEntity extends BaseEntity {

    private String firstName;
    private String lastName;
    private List<PostEntity> postEntities;

    public WriterEntity(Long id, @NonNull String firstName, @NonNull String lastName, List<PostEntity> postEntities) {
        this.setId(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.postEntities = postEntities;
    }

    public WriterEntity(@NonNull String firstName, @NonNull String lastName, List<PostEntity> postEntities) {
        this.setId(-1L);
        this.firstName = firstName;
        this.lastName = lastName;
        this.postEntities = postEntities;
    }
    public WriterEntity(@NonNull String firstName, @NonNull String lastName) {
        this.setId(-1L);
        this.firstName = firstName;
        this.lastName = lastName;
        this.postEntities = new ArrayList<>();
    }
}
