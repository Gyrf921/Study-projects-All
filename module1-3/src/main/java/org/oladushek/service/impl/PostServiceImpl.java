package org.oladushek.service.impl;

import lombok.AllArgsConstructor;
import org.oladushek.model.entity.PostEntity;
import org.oladushek.repository.PostRepository;
import org.oladushek.repository.impl.PostRepositoryImpl;
import org.oladushek.service.PostService;

import java.util.List;

@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

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
    public List<PostEntity> getPostsByTitle(String title) {
        return postRepository.findAll().stream()
                .filter(p -> p.getTitle().contains(title))
                .toList();
    }

    @Override
    public PostEntity create(PostEntity postEntity) {
        return postRepository.save(postEntity);
    }

    @Override
    public PostEntity update(PostEntity postEntity) {
        if (postRepository.findById(postEntity.getId()) != null) {
            return postRepository.update(postEntity);
        }
        else {
            System.out.println("Post " + postEntity.getId() + " not found. Try to create new Post.");
            return postRepository.save(postEntity);
        }
    }

    @Override
    public void delete(Long idToDelete) {
        postRepository.deleteById(idToDelete);
    }

}
