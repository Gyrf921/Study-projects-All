package org.oladushek.controller.dto.mapper;

import org.oladushek.controller.dto.PostDTO;
import org.oladushek.model.Label;
import org.oladushek.model.Post;

public class PostMapper implements GenericMapper<Post, PostDTO> {
    @Override
    public PostDTO mapToDTO(Post post) {
        return new PostDTO(post.getId(), post.getTitle(), post.getContent(), post.getPostLabels());
    }

    @Override
    public Post mapToEntity(PostDTO postDTO) {
        return new Post(postDTO.id(), postDTO.title(), postDTO.content(), postDTO.postLabels());
    }
}
