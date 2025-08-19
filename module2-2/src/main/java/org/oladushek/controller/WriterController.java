package org.oladushek.controller;

import org.oladushek.dto.WriterDTO;
import org.oladushek.mapper.WriterMapper;
import org.oladushek.entity.PostEntity;
import org.oladushek.entity.WriterEntity;
import org.oladushek.service.PostService;
import org.oladushek.service.WriterService;
import org.oladushek.service.impl.PostServiceImpl;
import org.oladushek.service.impl.WriterServiceImpl;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class WriterController {
    private final WriterMapper writerMapper;
    private final PostService postService;
    private final WriterService writerService;

    public WriterController(){
        this.writerMapper = new WriterMapper();
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
                postsId.isEmpty() ? Collections.emptyList() : getSelectedPostByIdForWriter(postsId));

        return writerMapper.mapToDTO(writerService.update(writerEntity));
    }

    public WriterDTO addNewPost(Long id, Long postId) {
        WriterEntity oldWriterEntity = writerService.getById(id);
        oldWriterEntity.getPostEntities().add(postService.getById(postId));
        return writerMapper.mapToDTO(writerService.update(oldWriterEntity));
    }

    private List<PostEntity> getSelectedPostByIdForWriter(List<Long> postsIdForNewWriter) {
        Predicate<PostEntity> isSelected = (post) -> postsIdForNewWriter.stream()
                .filter(selectedId -> post.getId().equals(selectedId))
                .findAny().orElse(null) != null;

        return postService.getAll().stream().filter(isSelected).toList();
    }
}
