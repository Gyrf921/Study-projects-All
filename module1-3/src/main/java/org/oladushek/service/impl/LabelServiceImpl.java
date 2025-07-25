package org.oladushek.service.impl;

import lombok.AllArgsConstructor;
import org.oladushek.model.entity.LabelEntity;
import org.oladushek.repository.LabelRepository;
import org.oladushek.repository.impl.LabelRepositoryImpl;
import org.oladushek.service.LabelService;

import java.util.List;

@AllArgsConstructor
public class LabelServiceImpl implements LabelService {

    private final LabelRepository labelRepository;

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
    public LabelEntity create(LabelEntity labelEntity) {
        return labelRepository.save(labelEntity);
    }

    @Override
    public LabelEntity update(LabelEntity labelEntity) {
        if (labelRepository.findById(labelEntity.getId()) != null) {
            return labelRepository.update(labelEntity);
        }
        else {
            System.out.println("Label " + labelEntity.getId() + " not found. Create new label.");
            return labelRepository.save(labelEntity);
        }
    }

    @Override
    public void delete(Long idToDelete) {
        labelRepository.deleteById(idToDelete);
    }

}
