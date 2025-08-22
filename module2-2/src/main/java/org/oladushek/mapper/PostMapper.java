package org.oladushek.mapper;

import org.oladushek.dto.PostDTO;
import org.oladushek.entity.PostEntity;
import org.oladushek.entity.enums.PostStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostMapper implements GenericMapper<PostEntity, PostDTO>{

    @Override
    public PostDTO mapToDTO(PostEntity postEntity) {
        return new PostDTO(postEntity.getId(), postEntity.getContent(), postEntity.getCreated(), postEntity.getUpdated(), postEntity.getPostLabelEntities(), postEntity.getStatus());
    }

    @Override
    public PostEntity mapToEntity(PostDTO postDTO) {
        return new PostEntity(postDTO.id(), postDTO.content(), postDTO.postLabelEntities(), postDTO.status());
    }

    public static List<PostEntity> mapResultSetToList(ResultSet rs) throws SQLException {
        Map<Long, PostEntity> map = new HashMap<>();
        while (rs.next()) {
            Long id = rs.getLong("id");
            PostEntity post = map.get(id);
            if(post == null){
                post = PostMapper.extractPostEntityWithoutLabels(rs);
                map.put(id, post);
            }
            post.getPostLabelEntities().add(LabelMapper.mapResultSetToLabelEntity(rs));
        }
        return new ArrayList<>(map.values());
    }

    public static PostEntity mapResultSetToPostEntity(ResultSet rs) throws SQLException {
        PostEntity postEntity = null;

        while (rs.next()) {
            if (postEntity == null) {
                postEntity = PostMapper.extractPostEntityWithoutLabels(rs);
            }
            postEntity.getPostLabelEntities().add(LabelMapper.mapResultSetToLabelEntity(rs));
        }
        return postEntity;
    }

    private static PostEntity extractPostEntityWithoutLabels(ResultSet rs) throws SQLException {
        PostEntity postEntity = new PostEntity();
        postEntity.setId(rs.getLong("id"));
        postEntity.setContent(rs.getString("content"));
        postEntity.setStatus(PostStatus.valueOf(rs.getString("status")));
        postEntity.setCreated(rs.getTimestamp("created").toLocalDateTime());
        postEntity.setUpdated(rs.getTimestamp("updated").toLocalDateTime());

        postEntity.setPostLabelEntities(new ArrayList<>());

        return postEntity;
    }

}
