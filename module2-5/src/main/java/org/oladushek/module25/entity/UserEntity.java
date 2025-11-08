package org.oladushek.module25.entity;

import jakarta.persistence.*;
import lombok.*;
import org.oladushek.module25.entity.base.BaseEntity;
import org.oladushek.module25.entity.base.UserStatus;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "status", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<EventEntity> events = new ArrayList<>();

}
