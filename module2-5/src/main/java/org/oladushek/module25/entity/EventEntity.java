package org.oladushek.module25.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.oladushek.module25.entity.base.BaseEntity;
import org.oladushek.module25.entity.base.EventStatus;

import java.time.LocalDateTime;

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

    @Column(name = "status", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private EventStatus status;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

}
