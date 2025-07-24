package org.oladushek.repository.impl;

import com.google.gson.reflect.TypeToken;
import org.oladushek.model.entity.WriterEntity;
import org.oladushek.model.Status;
import org.oladushek.repository.WriterRepository;
import org.oladushek.repository.generic.RepositoryFileHelper;
import org.oladushek.repository.generic.RepositoryFileHelperImpl;

import java.util.List;

public class WriterRepositoryImpl implements WriterRepository {
    private static final String WRITER_REPOSITORY_FILE = "src/main/resources/writer.json";
    private static final RepositoryFileHelper<WriterEntity> helper
            = new RepositoryFileHelperImpl<>(new TypeToken<List<WriterEntity>>() {}.getType());


    @Override
    public WriterEntity findById(Long id) {
        return helper.readAllWithoutFilter(WRITER_REPOSITORY_FILE).stream()
                .filter(writer -> writer.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public List<WriterEntity> findAll() {
        return helper.readAllWithoutFilter(WRITER_REPOSITORY_FILE).stream()
                .filter(writer -> writer.getStatus().equals(Status.ACTIVE))
                .toList();
    }

    @Override
    public WriterEntity save(WriterEntity writerEntityForSave) {
        List<WriterEntity> currentWriterEntities = helper.readAllWithoutFilter(WRITER_REPOSITORY_FILE);
        writerEntityForSave.setId(helper.generateAutoIncrementedId(currentWriterEntities));
        currentWriterEntities.add(writerEntityForSave);
        helper.writeAll(currentWriterEntities, WRITER_REPOSITORY_FILE);
        return writerEntityForSave;
    }

    @Override
    public WriterEntity update(WriterEntity writerEntityForUpdate) {
        List<WriterEntity> updatedWriterEntities = helper.readAllWithoutFilter(WRITER_REPOSITORY_FILE).stream()
                .map(currentWriter -> {
                    if (currentWriter.getId().equals(writerEntityForUpdate.getId())) {
                        return writerEntityForUpdate;
                    }
                    return currentWriter;
                }).toList();
        helper.writeAll(updatedWriterEntities, WRITER_REPOSITORY_FILE);
        return writerEntityForUpdate;
    }

    @Override
    public void deleteById(Long idForDelete) {
        List<WriterEntity> updatedWriterEntities = helper.readAllWithoutFilter(WRITER_REPOSITORY_FILE).stream()
                .map(currentWriter -> {
                    if (currentWriter.getId().equals(idForDelete)) {
                        currentWriter.setStatus(Status.DELETED);
                        return currentWriter;
                    }
                    return currentWriter;
                }).toList();
        helper.writeAll(updatedWriterEntities, WRITER_REPOSITORY_FILE);
    }
}
