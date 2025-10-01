package org.oladushek.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.oladushek.entity.FileEntity;

@Data
@AllArgsConstructor
public class FileDto {

    private String name;

    private String filePath;

    public FileDto(FileEntity file) {
        this.name = file.getName();
        this.filePath = file.getFilePath();
    }
}
