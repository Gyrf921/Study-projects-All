package org.oladushek.controller.dto.mapper;

import org.oladushek.controller.dto.LabelDTO;
import org.oladushek.model.entity.LabelEntity;

public class LabelMapper implements GenericMapper<LabelEntity, LabelDTO> {

    @Override
    public LabelDTO mapToDTO(LabelEntity l) {
        return new LabelDTO(l.getId(), l.getName());
    }

    @Override
    public LabelEntity mapToEntity(LabelDTO labelDTO) {
        return new LabelEntity(labelDTO.id(), labelDTO.name());
    }
}
