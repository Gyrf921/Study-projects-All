package org.oladushek.mapper;

import org.oladushek.dto.PostDTO;
import org.oladushek.entity.LabelEntity;
import org.oladushek.entity.PostEntity;
import org.oladushek.entity.enums.PostStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PostMapper implements GenericMapper<PostEntity, PostDTO>{

    @Override
    public PostDTO mapToDTO(PostEntity postEntity) {
        return new PostDTO(postEntity.getId(), postEntity.getContent(), postEntity.getCreated(), postEntity.getUpdated(), postEntity.getPostLabelEntities(), postEntity.getStatus());
    }

    @Override
    public PostEntity mapToEntity(PostDTO postDTO) {
        return new PostEntity(postDTO.id(), postDTO.content(), postDTO.postLabelEntities(), postDTO.status());
    }


    public PostEntity mapResultSet(ResultSet rs) throws SQLException {
        PostEntity post = new PostEntity();

        post.setId(rs.getLong("id"));
        post.setContent(rs.getString("content"));
        post.setStatus(PostStatus.valueOf(rs.getString("status")));
        post.setCreated(rs.getTimestamp("created").toLocalDateTime());
        post.setUpdated(rs.getTimestamp("updated").toLocalDateTime());
        post.setPostLabelEntities((List<LabelEntity>) rs.getArray("labels"));
        return post;
    }
}
