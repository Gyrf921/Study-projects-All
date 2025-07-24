package org.oladushek.repository.impl;

import com.google.gson.reflect.TypeToken;
import org.oladushek.model.entity.PostEntity;
import org.oladushek.model.Status;
import org.oladushek.repository.PostRepository;
import org.oladushek.repository.generic.RepositoryFileHelper;
import org.oladushek.repository.generic.RepositoryFileHelperImpl;

import java.util.List;

public class PostRepositoryImpl implements PostRepository{

    private static final String POST_REPOSITORY_FILE = "src/main/resources/posts.json";
    private static final RepositoryFileHelper<PostEntity> helper
            = new RepositoryFileHelperImpl<>(new TypeToken<List<PostEntity>>() {}.getType());

    @Override
    public PostEntity findById(Long id) {
        return helper.readAllWithoutFilter(POST_REPOSITORY_FILE).stream()
                .filter(post -> post.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public List<PostEntity> findAll() {
        return helper.readAllWithoutFilter(POST_REPOSITORY_FILE).stream()
                .filter(post -> post.getStatus().equals(Status.ACTIVE))
                .toList();
    }

    @Override
    public PostEntity save(PostEntity postEntityForSave) {
        List<PostEntity> currentPostEntities = helper.readAllWithoutFilter(POST_REPOSITORY_FILE);
        postEntityForSave.setId(helper.generateAutoIncrementedId(currentPostEntities));
        currentPostEntities.add(postEntityForSave);
        helper.writeAll(currentPostEntities, POST_REPOSITORY_FILE);
        return postEntityForSave;
    }

    @Override
    public PostEntity update(PostEntity postEntityForUpdate) {
        List<PostEntity> updatedPostEntities = helper.readAllWithoutFilter(POST_REPOSITORY_FILE).stream()
                .map(currentPost -> {
                    if (currentPost.getId().equals(postEntityForUpdate.getId())) {
                        return postEntityForUpdate;
                    }
                    return currentPost;
                }).toList();
        helper.writeAll(updatedPostEntities, POST_REPOSITORY_FILE);
        return postEntityForUpdate;
    }

    @Override
    public void deleteById(Long idForDelete) {
        List<PostEntity> updatedPostEntities = helper.readAllWithoutFilter(POST_REPOSITORY_FILE).stream()
                .map(currentPost -> {
                    if (currentPost.getId().equals(idForDelete)) {
                        currentPost.setStatus(Status.DELETED);
                        return currentPost;
                    }
                    return currentPost;
                }).toList();
        helper.writeAll(updatedPostEntities, POST_REPOSITORY_FILE);
    }

}
