package org.oladushek.controller;

import lombok.AllArgsConstructor;
import org.oladushek.model.Label;
import org.oladushek.service.LabelService;
import org.oladushek.controller.dto.LabelDTO;
import org.oladushek.controller.dto.mapper.LabelMapper;
import org.oladushek.service.impl.LabelServiceImpl;

import java.util.List;

@AllArgsConstructor
public class LabelController {

    private final LabelMapper mapper;
    private final LabelService labelService;

    public LabelController(){
        this.mapper = new LabelMapper();
        this.labelService = new LabelServiceImpl();
    }

    public LabelDTO getById(Long id) {
        return mapper.mapToDTO(labelService.getById(id));
    }

    public List<LabelDTO> getAll() {
        return labelService.getAll().stream()
                .map(mapper::mapToDTO)
                .toList();
    }

    public LabelDTO create(String name){
        return mapper.mapToDTO(labelService.create(new Label(name)));
    }

    public LabelDTO update(Long id, String newName) {
        return mapper.mapToDTO(labelService.update(new Label(id, newName)));
    }

    public void delete(Long id) {
        labelService.delete(id);
    }

}