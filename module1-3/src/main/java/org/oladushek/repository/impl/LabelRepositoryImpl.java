package org.oladushek.repository.impl;

import com.google.gson.reflect.TypeToken;
import org.oladushek.model.entity.LabelEntity;
import org.oladushek.model.Status;
import org.oladushek.repository.LabelRepository;
import org.oladushek.repository.generic.RepositoryFileHelper;
import org.oladushek.repository.generic.RepositoryFileHelperImpl;

import java.util.List;

public class LabelRepositoryImpl implements LabelRepository {

    private static final String LABEL_REPOSITORY_FILE = "src/main/resources/labels.json";
    private static final RepositoryFileHelper<LabelEntity> helper
            = new RepositoryFileHelperImpl<>(new TypeToken<List<LabelEntity>>() {}.getType());

    @Override
    public LabelEntity findById(Long id) {
        return helper.readAllWithoutFilter(LABEL_REPOSITORY_FILE).stream()
                .filter(label -> label.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public List<LabelEntity> findAll() {
        return helper.readAllWithoutFilter(LABEL_REPOSITORY_FILE).stream()
                .filter(label -> label.getStatus().equals(Status.ACTIVE))
                .toList();
    }

    @Override
    public LabelEntity save(LabelEntity labelEntity) {
        List<LabelEntity> currentLabelEntities = helper.readAllWithoutFilter(LABEL_REPOSITORY_FILE);
        labelEntity.setId(helper.generateAutoIncrementedId(currentLabelEntities));
        currentLabelEntities.add(labelEntity);
        helper.writeAll(currentLabelEntities, LABEL_REPOSITORY_FILE);
        return labelEntity;
    }

    @Override
    public LabelEntity update(LabelEntity updatedLabelEntity) {
        List<LabelEntity> updatedLabelEntities = helper.readAllWithoutFilter(LABEL_REPOSITORY_FILE).stream()
                .map(currentLabel -> {
                    if (currentLabel.getId().equals(updatedLabelEntity.getId())) {
                        return updatedLabelEntity;
                    }
                    return currentLabel;
                }).toList();
        helper.writeAll(updatedLabelEntities, LABEL_REPOSITORY_FILE);
        return updatedLabelEntity;
    }

    @Override
    public void deleteById(Long labelId) {
        List<LabelEntity> updatedLabelEntities = helper.readAllWithoutFilter(LABEL_REPOSITORY_FILE).stream()
                .map(currentLabel -> {
                    if (currentLabel.getId().equals(labelId)) {
                        currentLabel.setStatus(Status.DELETED);
                        return currentLabel;
                    }
                    return currentLabel;
                }).toList();
        helper.writeAll(updatedLabelEntities, LABEL_REPOSITORY_FILE);
    }
}
