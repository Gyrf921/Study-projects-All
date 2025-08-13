package org.oladushek.controller.dto.mapper;

import org.oladushek.controller.dto.LabelDTO;
import org.oladushek.entity.LabelEntity;

public class LabelMapper implements GenericMapper<LabelEntity, LabelDTO> {

    @Override
    public LabelDTO mapToDTO(LabelEntity entity) {
        return new LabelDTO(entity.getId(), entity.getName());
    }

    @Override
    public LabelEntity mapToEntity(LabelDTO labelDTO) {
        return new LabelEntity(labelDTO.id(), labelDTO.name());
    }
}
