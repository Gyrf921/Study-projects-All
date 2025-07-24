package org.oladushek.service;

import org.oladushek.model.entity.PostEntity;

import java.util.List;

public interface PostService extends GenericService<PostEntity, Long> {
    List<PostEntity> getPostsByTitle(String title);
}
