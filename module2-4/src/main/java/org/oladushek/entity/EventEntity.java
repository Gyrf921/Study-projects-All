package org.oladushek.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.oladushek.entity.base.BaseEntity;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "events")
public class EventEntity extends BaseEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToOne(optional=false, fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private FileEntity file;

    public EventEntity(UserEntity user, FileEntity file) {
        this.user = user;
        this.file = file;
    }
}
