package org.oladushek.service.impl;

import org.oladushek.model.Label;
import org.oladushek.repository.LabelRepository;
import org.oladushek.repository.impl.LabelRepositoryImpl;
import org.oladushek.service.LabelService;

import java.util.List;

public class LabelServiceImpl implements LabelService {

    private final LabelRepository labelRepository = new LabelRepositoryImpl();

    @Override
    public Label getById(Long id) {
        return labelRepository.findById(id);
    }

    @Override
    public List<Label> getAll() {
        return labelRepository.findAll();
    }

    @Override
    public Label create(Label label) {
        return labelRepository.save(label);
    }

    @Override
    public Label update(Label label) {
        if (labelRepository.findById(label.getId()) != null) {
            return labelRepository.update(label);
        }
        else {
            System.out.println("Label " + label.getId() + " not found. Create new label.");
            return labelRepository.save(label);
        }
    }

    @Override
    public void delete(Long idToDelete) {
        labelRepository.deleteById(idToDelete);
    }

}
