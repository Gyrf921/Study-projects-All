package org.oladushek.controller.dto.mapper;

import org.oladushek.controller.dto.WriterDTO;
import org.oladushek.model.Writer;

public class WriterMapper implements GenericMapper<Writer, WriterDTO>{

    @Override
    public WriterDTO mapToDTO(Writer writer) {
        return new WriterDTO(writer.getId(), writer.getFirstName(), writer.getLastName(), writer.getPosts());
    }

    @Override
    public Writer mapToEntity(WriterDTO writerDTO) {
        return new Writer(writerDTO.id(), writerDTO.firstName(), writerDTO.lastName(), writerDTO.posts());
    }
}
