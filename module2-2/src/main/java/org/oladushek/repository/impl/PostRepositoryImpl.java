package org.oladushek.repository.impl;

import org.oladushek.entity.PostEntity;
import org.oladushek.repository.PostRepository;

import java.util.List;

public class PostRepositoryImpl implements PostRepository {

    @Override
    public PostEntity findById(Long aLong) {
        return null;
    }

    @Override
    public List<PostEntity> findXNew(int count) {
        return List.of();
    }

    @Override
    public List<PostEntity> findAll() {
        return List.of();
    }

    @Override
    public PostEntity save(PostEntity postEntity) {
        return null;
    }

    @Override
    public PostEntity update(PostEntity postEntity) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }

}
