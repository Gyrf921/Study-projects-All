package org.oladushek.controller.dto.mapper;

import org.oladushek.controller.dto.PostDTO;
import org.oladushek.model.entity.PostEntity;

public class PostMapper implements GenericMapper<PostEntity, PostDTO> {
    @Override
    public PostDTO mapToDTO(PostEntity postEntity) {
        return new PostDTO(postEntity.getId(), postEntity.getTitle(), postEntity.getContent(), postEntity.getPostLabelEntities());
    }

    @Override
    public PostEntity mapToEntity(PostDTO postDTO) {
        return new PostEntity(postDTO.id(), postDTO.title(), postDTO.content(), postDTO.postLabelEntities());
    }
}
