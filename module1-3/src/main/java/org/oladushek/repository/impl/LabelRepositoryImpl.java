package org.oladushek.repository.impl;

import com.google.gson.reflect.TypeToken;
import org.oladushek.model.Label;
import org.oladushek.model.Status;
import org.oladushek.repository.LabelRepository;
import org.oladushek.repository.generic.RepositoryFileHelper;
import org.oladushek.repository.generic.RepositoryFileHelperImpl;

import java.util.List;

public class LabelRepositoryImpl implements LabelRepository {

    private static final String LABEL_REPOSITORY_FILE = "src/main/resources/labels.json";
    private static final RepositoryFileHelper<Label> helper
            = new RepositoryFileHelperImpl<>(new TypeToken<List<Label>>() {}.getType());

    @Override
    public Label findById(Long id) {
        return helper.readAllWithoutFilter(LABEL_REPOSITORY_FILE).stream()
                .filter(label -> label.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public List<Label> findAll() {
        return helper.readAllWithoutFilter(LABEL_REPOSITORY_FILE).stream()
                .filter(label -> label.getStatus().equals(Status.ACTIVE))
                .toList();
    }

    @Override
    public Label save(Label label) {
        List<Label> currentLabels = helper.readAllWithoutFilter(LABEL_REPOSITORY_FILE);
        label.setId(helper.generateAutoIncrementedId(currentLabels));
        currentLabels.add(label);
        helper.writeAll(currentLabels, LABEL_REPOSITORY_FILE);
        return label;
    }

    @Override
    public Label update(Label updatedLabel) {
        List<Label> updatedLabels = helper.readAllWithoutFilter(LABEL_REPOSITORY_FILE).stream()
                .map(currentLabel -> {
                    if (currentLabel.getId().equals(updatedLabel.getId())) {
                        return updatedLabel;
                    }
                    return currentLabel;
                }).toList();
        helper.writeAll(updatedLabels, LABEL_REPOSITORY_FILE);
        return updatedLabel;
    }

    @Override
    public void deleteById(Long labelId) {
        List<Label> updatedLabels = helper.readAllWithoutFilter(LABEL_REPOSITORY_FILE).stream()
                .map(currentLabel -> {
                    if (currentLabel.getId().equals(labelId)) {
                        currentLabel.setStatus(Status.DELETED);
                        return currentLabel;
                    }
                    return currentLabel;
                }).toList();
        helper.writeAll(updatedLabels, LABEL_REPOSITORY_FILE);
    }
}
