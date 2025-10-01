package org.oladushek.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.oladushek.entity.base.BaseEntity;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "files")
public class FileEntity extends BaseEntity {

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    public FileEntity(String name, String filePath) {
        this.name = name;
        this.filePath = filePath;
    }
}
