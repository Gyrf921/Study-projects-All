package org.oladushek.service;

import org.oladushek.controller.dto.mapper.GenericMapper;
import org.oladushek.model.Post;

import java.util.List;

public interface PostService extends GenericService<Post, Long> {
    List<Post> getPostsByTitle(String title);
}
