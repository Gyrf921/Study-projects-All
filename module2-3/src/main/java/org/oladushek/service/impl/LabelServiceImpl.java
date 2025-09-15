package org.oladushek.service.impl;

import org.oladushek.entity.LabelEntity;
import org.oladushek.repository.LabelRepository;
import org.oladushek.repository.impl.LabelRepositoryImpl;
import org.oladushek.service.LabelService;

import java.util.List;
import java.util.function.Predicate;

public class LabelServiceImpl implements LabelService {

    private final LabelRepository labelRepository;

    public LabelServiceImpl(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }
    public LabelServiceImpl(){
       labelRepository = new LabelRepositoryImpl();
    }

    @Override
    public LabelEntity getById(Long id) {
        return labelRepository.findById(id);
    }

    @Override
    public List<LabelEntity> getAll() {
        return labelRepository.findAll();
    }

    @Override
    public List<LabelEntity> getSelectedLabelByIdForPost(List<Long> labelsIdForNewPost) {
        Predicate<LabelEntity> isSelected = (label) -> labelsIdForNewPost.stream()
                .filter(selectedId -> label.getId().equals(selectedId))
                .findAny().orElse(null) != null;

        return getAll().stream().filter(isSelected).toList();
    }

    @Override
    public LabelEntity create(LabelEntity labelEntity) {
        return labelRepository.save(labelEntity);
    }

    @Override
    public LabelEntity update(Long id, LabelEntity labelEntity) {
        return labelRepository.update(id, labelEntity);
    }

    @Override
    public void delete(Long idToDelete) {
        labelRepository.deleteById(idToDelete);
    }



}
