package org.oladushek.controller.dto.mapper;

import org.oladushek.controller.dto.PostDTO;
import org.oladushek.entity.PostEntity;

public class PostMapper implements GenericMapper<PostEntity, PostDTO>{

    @Override
    public PostDTO mapToDTO(PostEntity postEntity) {
        return new PostDTO(postEntity.getId(), postEntity.getContent(), postEntity.getCreated(), postEntity.getUpdated(), postEntity.getPostLabelEntities(), postEntity.getStatus());
    }

    @Override
    public PostEntity mapToEntity(PostDTO postDTO) {
        return new PostEntity(postDTO.id(), postDTO.content(), postDTO.postLabelEntities(), postDTO.status());
    }
}
