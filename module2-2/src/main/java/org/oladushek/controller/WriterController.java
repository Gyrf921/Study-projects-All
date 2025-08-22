package org.oladushek.controller;

import org.oladushek.dto.WriterDTO;
import org.oladushek.entity.LabelEntity;
import org.oladushek.mapper.WriterMapper;
import org.oladushek.entity.PostEntity;
import org.oladushek.entity.WriterEntity;
import org.oladushek.service.LabelService;
import org.oladushek.service.PostService;
import org.oladushek.service.WriterService;
import org.oladushek.service.impl.LabelServiceImpl;
import org.oladushek.service.impl.PostServiceImpl;
import org.oladushek.service.impl.WriterServiceImpl;

import java.util.List;
import java.util.function.Predicate;

public class WriterController {
    private final WriterMapper writerMapper;
    private final LabelService labelService;
    private final PostService postService;
    private final WriterService writerService;

    public WriterController(){
        this.writerMapper = new WriterMapper();
        this.labelService = new LabelServiceImpl();
        this.postService = new PostServiceImpl();
        this.writerService = new WriterServiceImpl();
    }

    public WriterDTO getById(Long id) {
        return writerMapper.mapToDTO(writerService.getById(id));
    }

    public List<WriterDTO> getAll() {
        return writerService.getAll().stream()
                .map(writerMapper::mapToDTO)
                .toList();
    }

    public void delete(Long id) {
        writerService.delete(id);
    }

    public WriterDTO createWithPosts(String firstName, String lastName, List<Long> postsId) {
        return writerMapper.mapToDTO(writerService.create(new WriterEntity(firstName, lastName, getSelectedPostByIdForWriter(postsId))));
    }

    public WriterDTO createWithoutPosts(String firstName, String lastName) {
        return writerMapper.mapToDTO(writerService.create(new WriterEntity(firstName, lastName)));
    }
    public WriterDTO update(Long id, String firstName, String lastName, List<Long> postsId) {
        WriterEntity oldWriterEntity = writerService.getById(id);
        WriterEntity writerEntity = new WriterEntity(id,
                firstName.equals("exit") ? oldWriterEntity.getFirstName() : firstName,
                lastName.equals("exit") ? oldWriterEntity.getLastName() : lastName,
                postsId.isEmpty() ? oldWriterEntity.getPostEntities() : getSelectedPostByIdForWriter(postsId));

        return writerMapper.mapToDTO(writerService.update(writerEntity));
    }

    public WriterDTO createNewPost(Long writerId, String contentForPost, List<Long> labelsForPost) {
        WriterEntity oldWriterEntity = writerService.getById(writerId);
        PostEntity postEntity = postService.create(new PostEntity(contentForPost, getSelectedLabelByIdForPost(labelsForPost)));

        oldWriterEntity.getPostEntities().add(postService.getById(postEntity.getId()));
        return writerMapper.mapToDTO(writerService.update(oldWriterEntity));
    }

    private List<PostEntity> getSelectedPostByIdForWriter(List<Long> postsIdForNewWriter) {
        Predicate<PostEntity> isSelected = (post) -> postsIdForNewWriter.stream()
                .filter(selectedId -> post.getId().equals(selectedId))
                .findAny().orElse(null) != null;

        return postService.getAll().stream().filter(isSelected).toList();
    }
    private List<LabelEntity> getSelectedLabelByIdForPost(List<Long> labelsIdForNewPost) {
        Predicate<LabelEntity> isSelected = (label) -> labelsIdForNewPost.stream()
                .filter(selectedId -> label.getId().equals(selectedId))
                .findAny().orElse(null) != null;

        return labelService.getAll().stream().filter(isSelected).toList();
    }
}
