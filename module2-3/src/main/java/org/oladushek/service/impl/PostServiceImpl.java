package org.oladushek.service.impl;

import org.oladushek.entity.PostEntity;
import org.oladushek.repository.PostRepository;
import org.oladushek.repository.impl.PostRepositoryImpl;
import org.oladushek.service.PostService;

import java.util.List;


public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
    public PostServiceImpl(){
        postRepository = new PostRepositoryImpl();
    }

    @Override
    public PostEntity getById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public List<PostEntity> getAll() {
        return postRepository.findAll();
    }

    @Override
    public List<PostEntity> getXNewCreated(int count) {
        return postRepository.findXNew(count);
    }

    @Override
    public PostEntity create(PostEntity postEntity) {
        return postRepository.save(postEntity);
    }

    @Override
    public PostEntity update(Long id, PostEntity postEntity) {
        return postRepository.update(id, postEntity);
    }

    @Override
    public void delete(Long idToDelete) {
        postRepository.deleteById(idToDelete);
    }

}
