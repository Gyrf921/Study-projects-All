package org.oladushek.controller.dto.mapper;

import org.oladushek.controller.dto.WriterDTO;
import org.oladushek.entity.WriterEntity;

public class WriterMapper implements GenericMapper<WriterEntity, WriterDTO>{
    @Override
    public WriterDTO mapToDTO(WriterEntity writerEntity) {
        return new WriterDTO(writerEntity.getId(), writerEntity.getFirstName(), writerEntity.getLastName(), writerEntity.getPostEntities());
    }

    @Override
    public WriterEntity mapToEntity(WriterDTO writerDTO) {
        return new WriterEntity(writerDTO.id(), writerDTO.firstName(), writerDTO.lastName(), writerDTO.postEntities());
    }
}
