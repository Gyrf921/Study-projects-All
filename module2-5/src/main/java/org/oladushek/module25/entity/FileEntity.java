package org.oladushek.module25.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.oladushek.module25.entity.base.BaseEntity;
import org.oladushek.module25.entity.base.FileStatus;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "files")
public class FileEntity extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    /** MinIO S3 URL */
    @Column(name = "location", nullable = false, length = 500)
    private String location;

    @Column(name = "status", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private FileStatus status;
}
