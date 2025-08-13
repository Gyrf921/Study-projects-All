package org.oladushek.controller;

import lombok.AllArgsConstructor;
import org.oladushek.controller.dto.LabelDTO;
import org.oladushek.controller.dto.mapper.LabelMapper;
import org.oladushek.entity.LabelEntity;
import org.oladushek.service.impl.LabelServiceImpl;

import java.util.List;

@AllArgsConstructor
public class LabelController implements GenericController<LabelDTO, Long> {

    private final LabelServiceImpl labelService;
    private final LabelMapper labelMapper;

    public LabelController(){
        this.labelMapper = new LabelMapper();
        this.labelService = new LabelServiceImpl();
    }

    @Override
    public LabelDTO getById(Long id) {
        return labelMapper.mapToDTO(labelService.getById(id));
    }

    @Override
    public List<LabelDTO> getAll() {
        return labelService.getAll().stream()
                .map(labelMapper::mapToDTO)
                .toList();
    }

    public LabelDTO create(String name){
        return labelMapper.mapToDTO(labelService.create(new LabelEntity(name)));
    }

    public LabelDTO update(Long id, String newName) {
        return labelMapper.mapToDTO(labelService.update(new LabelEntity(id, newName)));
    }

    @Override
    public void delete(Long id) {
        labelService.delete(id);
    }
}
