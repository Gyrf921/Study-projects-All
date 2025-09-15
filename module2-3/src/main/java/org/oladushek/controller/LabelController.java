package org.oladushek.controller;

import lombok.AllArgsConstructor;
import org.oladushek.dto.LabelDTO;
import org.oladushek.entity.LabelEntity;
import org.oladushek.mapper.LabelMapper;
import org.oladushek.service.impl.LabelServiceImpl;

import java.util.List;

@AllArgsConstructor
public class LabelController {

    private final LabelServiceImpl labelService;
    private final LabelMapper labelMapper;

    public LabelController(){
        this.labelMapper = new LabelMapper();
        this.labelService = new LabelServiceImpl();
    }


    public LabelDTO getById(Long id) {
        return labelMapper.mapToDTO(labelService.getById(id));
    }


    public List<LabelDTO> getAll() {
        return labelService.getAll().stream()
                .map(labelMapper::mapToDTO)
                .toList();
    }

    public LabelDTO create(String name){
        return labelMapper.mapToDTO(labelService.create(new LabelEntity(name)));
    }

    public LabelDTO update(Long id, String newName) {
        return labelMapper.mapToDTO(labelService.update(id, new LabelEntity(newName)));
    }


    public void delete(Long id) {
        labelService.delete(id);
    }
}
