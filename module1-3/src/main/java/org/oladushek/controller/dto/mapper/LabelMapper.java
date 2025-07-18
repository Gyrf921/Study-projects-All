package org.oladushek.controller.dto.mapper;

import org.oladushek.controller.dto.LabelDTO;
import org.oladushek.model.Label;

public class LabelMapper implements GenericMapper<Label, LabelDTO> {

    @Override
    public LabelDTO mapToDTO(Label l) {
        return new LabelDTO(l.getId(), l.getName());
    }

    @Override
    public Label mapToEntity(LabelDTO labelDTO) {
        return new Label(labelDTO.id(), labelDTO.name());
    }
}
