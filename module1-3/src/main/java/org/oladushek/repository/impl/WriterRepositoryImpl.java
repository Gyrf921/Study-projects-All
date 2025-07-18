package org.oladushek.repository.impl;

import com.google.gson.reflect.TypeToken;
import org.oladushek.model.Writer;
import org.oladushek.model.Status;
import org.oladushek.model.Writer;
import org.oladushek.repository.WriterRepository;
import org.oladushek.repository.generic.RepositoryFileHelper;
import org.oladushek.repository.generic.RepositoryFileHelperImpl;

import java.util.List;

public class WriterRepositoryImpl implements WriterRepository {
    private static final String WRITER_REPOSITORY_FILE = "src/main/resources/writer.json";
    private static final RepositoryFileHelper<Writer> helper
            = new RepositoryFileHelperImpl<>(new TypeToken<List<Writer>>() {}.getType());


    @Override
    public Writer findById(Long id) {
        return helper.readAllWithoutFilter(WRITER_REPOSITORY_FILE).stream()
                .filter(writer -> writer.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public List<Writer> findAll() {
        return helper.readAllWithoutFilter(WRITER_REPOSITORY_FILE).stream()
                .filter(writer -> writer.getStatus().equals(Status.ACTIVE))
                .toList();
    }

    @Override
    public Writer save(Writer writerForSave) {
        List<Writer> currentWriters = helper.readAllWithoutFilter(WRITER_REPOSITORY_FILE);
        writerForSave.setId(helper.generateAutoIncrementedId(currentWriters));
        currentWriters.add(writerForSave);
        helper.writeAll(currentWriters, WRITER_REPOSITORY_FILE);
        return writerForSave;
    }

    @Override
    public Writer update(Writer writerForUpdate) {
        List<Writer> updatedWriters = helper.readAllWithoutFilter(WRITER_REPOSITORY_FILE).stream()
                .map(currentWriter -> {
                    if (currentWriter.getId().equals(writerForUpdate.getId())) {
                        return writerForUpdate;
                    }
                    return currentWriter;
                }).toList();
        helper.writeAll(updatedWriters, WRITER_REPOSITORY_FILE);
        return writerForUpdate;
    }

    @Override
    public void deleteById(Long idForDelete) {
        List<Writer> updatedWriters = helper.readAllWithoutFilter(WRITER_REPOSITORY_FILE).stream()
                .map(currentWriter -> {
                    if (currentWriter.getId().equals(idForDelete)) {
                        currentWriter.setStatus(Status.DELETED);
                        return currentWriter;
                    }
                    return currentWriter;
                }).toList();
        helper.writeAll(updatedWriters, WRITER_REPOSITORY_FILE);
    }
}
