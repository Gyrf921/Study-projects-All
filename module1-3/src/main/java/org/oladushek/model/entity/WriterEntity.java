package org.oladushek.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.oladushek.model.Status;
import org.oladushek.model.StatusEntity;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class WriterEntity extends StatusEntity {

    private String firstName;
    private String lastName;
    private List<PostEntity> postEntities;


    public WriterEntity(Long id, @NonNull String firstName, @NonNull String lastName, List<PostEntity> postEntities) {
        this.setId(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.postEntities = postEntities;
        this.setStatus(Status.ACTIVE);
    }

    public WriterEntity(@NonNull String firstName, @NonNull String lastName, List<PostEntity> postEntities) {
        this.setId(-1L);
        this.firstName = firstName;
        this.lastName = lastName;
        this.postEntities = postEntities;
        this.setStatus(Status.ACTIVE);
    }
    public WriterEntity(@NonNull String firstName, @NonNull String lastName) {
        this.setId(-1L);
        this.firstName = firstName;
        this.lastName = lastName;
        this.postEntities = new ArrayList<>();
        this.setStatus(Status.ACTIVE);
    }
}
