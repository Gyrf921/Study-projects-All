package org.oladushek.module25.entity;

import jakarta.persistence.*;
import lombok.*;
import org.oladushek.module25.entity.base.BaseEntity;
import org.oladushek.module25.entity.base.UserRole;
import org.oladushek.module25.entity.base.UserStatus;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false, length = 2048)
    private String password;

    @Column(name = "role", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private UserRole role;

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

    @ToString.Include(name = "password")
    private String maskPassword() {
        return "**********";
    }

    public boolean isActive(){
        return UserStatus.ACTIVE.equals(userStatus);
    }
}
