package org.oladushek.service.impl;

import org.oladushek.config.HibernateConfig;
import org.oladushek.config.PropertiesConfig;
import org.oladushek.entity.EventEntity;
import org.oladushek.entity.FileEntity;
import org.oladushek.entity.UserEntity;
import org.oladushek.service.FileService;

import java.io.File;

public class FileServiceImpl implements FileService {

    private static final String UPLOAD_DIR = PropertiesConfig.getProperty("upload.dir");

    @Override
    public FileEntity getByName(String name) {
        return HibernateConfig.getSessionFactory()
                .fromTransaction(session ->
                        session.createSelectionQuery("from FileEntity where name = ?1", FileEntity.class)
                                .setParameter(1, name)
                                .getSingleResult());
    }

    @Override
    public FileEntity create(String fileName, Long userId) {
        String path = createDir();

        return HibernateConfig.getSessionFactory().fromTransaction(session -> {
            UserEntity user = session.find(UserEntity.class, userId);
            if (user == null) {
                return null;
            }
            FileEntity file = new FileEntity(fileName, path + fileName);
            session.persist(file);
            session.persist(new EventEntity(user, file));
            return file;
        });
    }

    @Override
    public FileEntity update(Long fileId, String fileName) {
        return HibernateConfig.getSessionFactory().fromTransaction(session -> {
            FileEntity oldFile = session.find(FileEntity.class, fileId);
            if (oldFile == null) {
                return null;
            }
            boolean isDeleted = new File(oldFile.getFilePath()).delete();
            if (isDeleted) {
                oldFile.setName(fileName);
                oldFile.setFilePath(createDir() + fileName);
            }
            return oldFile;
        });
    }


    @Override
    public boolean delete(Long fileId) {
        return HibernateConfig.getSessionFactory()
                .fromTransaction(session -> {
                    FileEntity fileForDelete = session.find(FileEntity.class, fileId);
                    boolean isDeleted = new File(fileForDelete.getFilePath()).delete();
                    if (isDeleted)
                        session.remove(fileForDelete);

                    return isDeleted;
                });
    }

    private String createDir(){
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        return uploadDir.getAbsolutePath() + File.separator;
    };
}
