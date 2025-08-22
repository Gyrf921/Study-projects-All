package org.oladushek.service;

import org.oladushek.entity.PostEntity;

import java.util.Collection;
import java.util.List;

public interface PostService extends GenericService<PostEntity, Long> {
    List<PostEntity> getXNewCreated(int count);

    List<PostEntity> getAllWithoutWriter();
}
