package org.oladushek.service;

import org.oladushek.entity.FileEntity;

public interface FileService {
    FileEntity getByName(String name);

    FileEntity create(String fileName, Long userId);

    FileEntity update(Long fileId, String fileName);

    boolean delete(Long fileId);
}
