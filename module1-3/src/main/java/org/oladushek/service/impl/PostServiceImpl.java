package org.oladushek.service.impl;

import org.oladushek.model.Post;
import org.oladushek.repository.LabelRepository;
import org.oladushek.repository.PostRepository;
import org.oladushek.repository.impl.LabelRepositoryImpl;
import org.oladushek.repository.impl.PostRepositoryImpl;
import org.oladushek.service.PostService;

import java.util.List;
import java.util.stream.Collectors;

public class PostServiceImpl implements PostService {

    private final PostRepository postRepository = new PostRepositoryImpl();

    @Override
    public Post getById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public List<Post> getAll() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> getPostsByTitle(String title) {
        return postRepository.findAll().stream()
                .filter(p -> p.getTitle().contains(title))
                .toList();
    }

    @Override
    public Post create(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post update(Post post) {
        if (postRepository.findById(post.getId()) != null) {
            return postRepository.update(post);
        }
        else {
            System.out.println("Post " + post.getId() + " not found. Try to create new Post.");
            return postRepository.save(post);
        }
    }

    @Override
    public void delete(Long idToDelete) {
        postRepository.deleteById(idToDelete);
    }

}
