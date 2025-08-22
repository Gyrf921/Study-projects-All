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
        return new WriterDTO(writerEntity.getId(), writerEntity.getFirstName(), writerEntity.getLastName(), writerEntity.getPostEntities());
    }

    @Override
    public WriterEntity mapToEntity(WriterDTO writerDTO) {
        return new WriterEntity(writerDTO.id(), writerDTO.firstName(), writerDTO.lastName(), writerDTO.postEntities());
    }

    public static List<WriterEntity> mapResultSetToList(ResultSet rs) throws SQLException {
        Map<Long, WriterEntity> map = new HashMap<>();
        while (rs.next()) {
            Long id = rs.getLong("id");
            WriterEntity writer = map.get(id);
            if(writer == null){
                writer = extractWriterEntity(rs);
                map.put(id, writer);
            }
            PostEntity pe = extractPostEntityWithoutLabels(rs);
            if (pe != null){
                writer.getPostEntities().add(pe);
            }
        }
        return new ArrayList<>(map.values());
    }

    public static WriterEntity mapResultSetToWriterEntity(ResultSet rs) throws SQLException {
        WriterEntity writerEntity = null;

        while (rs.next()) {
            if (writerEntity == null) {
                writerEntity = WriterMapper.extractWriterEntity(rs);
            }
            PostEntity pe = extractPostEntityWithoutLabels(rs);
            if (pe != null){
                writerEntity.getPostEntities().add(pe);
            }
        }
        return writerEntity;
    }

    private static WriterEntity extractWriterEntity(ResultSet rs) throws SQLException {
        WriterEntity writerEntity = new WriterEntity();
        writerEntity.setId(rs.getLong("id"));
        writerEntity.setFirstName(rs.getString("first_name"));
        writerEntity.setLastName(rs.getString("last_name"));

        writerEntity.setPostEntities(new ArrayList<>());

        return writerEntity;
    }

    private static PostEntity extractPostEntityWithoutLabels(ResultSet rs) throws SQLException {
        PostEntity postEntity = new PostEntity();
        if (rs.getLong("post_id") != 0){
            postEntity.setId(rs.getLong("post_id"));
            postEntity.setContent(rs.getString("content"));
            postEntity.setStatus(PostStatus.valueOf(rs.getString("status")));
            postEntity.setCreated(rs.getTimestamp("created").toLocalDateTime());
            postEntity.setUpdated(rs.getTimestamp("updated").toLocalDateTime());

            postEntity.setPostLabelEntities(new ArrayList<>());
            return postEntity;
        }
        return null;
    }
}
