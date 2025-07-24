package org.oladushek.controller;

import lombok.AllArgsConstructor;
import org.oladushek.controller.dto.WriterDTO;
import org.oladushek.controller.dto.mapper.WriterMapper;
import org.oladushek.model.entity.PostEntity;
import org.oladushek.model.entity.WriterEntity;
import org.oladushek.service.PostService;
import org.oladushek.service.WriterService;
import org.oladushek.service.impl.PostServiceImpl;
import org.oladushek.service.impl.WriterServiceImpl;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

@AllArgsConstructor
public class WriterController {
    private final WriterMapper mapper;
    private final PostService postService;
    private final WriterService writerService;

    public WriterController(){
        this.mapper = new WriterMapper();
        this.postService = new PostServiceImpl();
        this.writerService = new WriterServiceImpl();
    }
    public WriterDTO getById(Long id) {
        return mapper.mapToDTO(writerService.getById(id));
    }

    public List<WriterDTO> getAll() {
        return writerService.getAll().stream()
                .map(mapper::mapToDTO)
                .toList();
    }

    public WriterDTO createWithPosts(String firstName, String lastName, List<Long> postsId) {
        return mapper.mapToDTO(writerService.create(new WriterEntity(firstName, lastName, getSelectedPostByIdForWriter(postsId))));
    }

    public WriterDTO createWithoutPosts(String firstName, String lastName) {
        return mapper.mapToDTO(writerService.create(new WriterEntity(firstName, lastName)));
    }

    public void delete(Long id) {
        writerService.delete(id);
    }

    public WriterDTO update(Long id, String firstName, String lastName, List<Long> postsId) {
        WriterEntity oldWriterEntity = writerService.getById(id);
        WriterEntity writerEntity = new WriterEntity(id,
                firstName.equals("exit") ? oldWriterEntity.getFirstName() : firstName,
                lastName.equals("exit") ? oldWriterEntity.getLastName() : lastName,
                postsId.isEmpty() ? Collections.emptyList() : getSelectedPostByIdForWriter(postsId));

        return mapper.mapToDTO(writerService.update(writerEntity));
    }

    public WriterDTO addNewPost(Long id, Long postId) {
        WriterEntity oldWriterEntity = writerService.getById(id);
        oldWriterEntity.getPostEntities().add(postService.getById(postId));
        return mapper.mapToDTO(writerService.update(oldWriterEntity));
    }

    private List<PostEntity> getSelectedPostByIdForWriter(List<Long> postsIdForNewWriter) {
        Predicate<PostEntity> isSelected = (post) -> postsIdForNewWriter.stream()
                .filter(selectedId -> post.getId().equals(selectedId))
                .findAny().orElse(null) != null;

        return postService.getAll().stream().filter(isSelected).toList();
    }

}
