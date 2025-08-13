package org.oladushek.controller.dto.mapper;

public interface GenericMapper<ENTITY, DTO> {
    DTO mapToDTO(ENTITY entity);
    ENTITY mapToEntity(DTO dto);
}
