package org.oladushek.entity;


import jakarta.persistence.*;
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
@Entity
@Table(name = "writers")
public class WriterEntity extends BaseEntity {

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @OneToMany(
            mappedBy = "writer",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<PostEntity> posts = new ArrayList<>();

    public WriterEntity(@NonNull String firstName, @NonNull String lastName, List<PostEntity> postEntities) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.posts = postEntities;
    }

    public WriterEntity(@NonNull String firstName, @NonNull String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.posts = new ArrayList<>();
    }
}
