package org.oladushek.mapper;

import org.oladushek.dto.LabelDTO;
import org.oladushek.entity.LabelEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LabelMapper implements GenericMapper<LabelEntity, LabelDTO> {

    @Override
    public LabelDTO mapToDTO(LabelEntity entity) {
        return new LabelDTO(entity.getId(), entity.getName());
    }

    @Override
    public LabelEntity mapToEntity(LabelDTO labelDTO) {
        return new LabelEntity(labelDTO.name());
    }

}
