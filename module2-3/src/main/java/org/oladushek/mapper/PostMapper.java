package org.oladushek.mapper;

import org.oladushek.dto.PostDTO;
import org.oladushek.entity.PostEntity;

public class PostMapper implements GenericMapper<PostEntity, PostDTO>{

    @Override
    public PostDTO mapToDTO(PostEntity postEntity) {
        return new PostDTO(postEntity.getId(), postEntity.getContent(), postEntity.getCreated(), postEntity.getUpdated(), postEntity.getLabels(), postEntity.getWriter(), postEntity.getStatus());
    }

    @Override
    public PostEntity mapToEntity(PostDTO postDTO) {
        return new PostEntity(postDTO.content(), postDTO.writerEntity(), postDTO.postLabelEntities(),  postDTO.status());
    }

}
