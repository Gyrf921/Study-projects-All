package org.oladushek.repository.impl;

import com.google.gson.reflect.TypeToken;
import org.oladushek.model.Post;
import org.oladushek.model.Status;
import org.oladushek.repository.PostRepository;
import org.oladushek.repository.generic.RepositoryFileHelper;
import org.oladushek.repository.generic.RepositoryFileHelperImpl;

import java.util.List;

public class PostRepositoryImpl implements PostRepository{

    private static final String POST_REPOSITORY_FILE = "src/main/resources/posts.json";
    private static final RepositoryFileHelper<Post> helper
            = new RepositoryFileHelperImpl<>(new TypeToken<List<Post>>() {}.getType());

    @Override
    public Post findById(Long id) {
        return helper.readAllWithoutFilter(POST_REPOSITORY_FILE).stream()
                .filter(post -> post.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public List<Post> findAll() {
        return helper.readAllWithoutFilter(POST_REPOSITORY_FILE).stream()
                .filter(post -> post.getStatus().equals(Status.ACTIVE))
                .toList();
    }

    @Override
    public Post save(Post postForSave) {
        List<Post> currentPosts = helper.readAllWithoutFilter(POST_REPOSITORY_FILE);
        postForSave.setId(helper.generateAutoIncrementedId(currentPosts));
        currentPosts.add(postForSave);
        helper.writeAll(currentPosts, POST_REPOSITORY_FILE);
        return postForSave;
    }

    @Override
    public Post update(Post postForUpdate) {
        List<Post> updatedPosts = helper.readAllWithoutFilter(POST_REPOSITORY_FILE).stream()
                .map(currentPost -> {
                    if (currentPost.getId().equals(postForUpdate.getId())) {
                        return postForUpdate;
                    }
                    return currentPost;
                }).toList();
        helper.writeAll(updatedPosts, POST_REPOSITORY_FILE);
        return postForUpdate;
    }

    @Override
    public void deleteById(Long idForDelete) {
        List<Post> updatedPosts = helper.readAllWithoutFilter(POST_REPOSITORY_FILE).stream()
                .map(currentPost -> {
                    if (currentPost.getId().equals(idForDelete)) {
                        currentPost.setStatus(Status.DELETED);
                        return currentPost;
                    }
                    return currentPost;
                }).toList();
        helper.writeAll(updatedPosts, POST_REPOSITORY_FILE);
    }

}
