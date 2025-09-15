package org.oladushek.mapper;

import org.oladushek.dto.WriterDTO;
import org.oladushek.entity.PostEntity;
import org.oladushek.entity.WriterEntity;
import org.oladushek.entity.enums.PostStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WriterMapper implements GenericMapper<WriterEntity, WriterDTO>{
    @Override
    public WriterDTO mapToDTO(WriterEntity writerEntity) {
        return new WriterDTO(writerEntity.getId(), writerEntity.getFirstName(), writerEntity.getLastName(), writerEntity.getPosts());
    }

    @Override
    public WriterEntity mapToEntity(WriterDTO writerDTO) {
        return new WriterEntity(writerDTO.firstName(), writerDTO.lastName(), writerDTO.postEntities());
    }

}
