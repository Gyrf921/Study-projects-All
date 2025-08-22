package org.oladushek.controller;

import lombok.AllArgsConstructor;
import org.oladushek.dto.PostDTO;
import org.oladushek.mapper.PostMapper;
import org.oladushek.entity.LabelEntity;
import org.oladushek.entity.PostEntity;
import org.oladushek.service.LabelService;
import org.oladushek.service.PostService;
import org.oladushek.service.impl.LabelServiceImpl;
import org.oladushek.service.impl.PostServiceImpl;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

@AllArgsConstructor
public class PostController {

    private final PostMapper mapper;
    private final LabelService labelService;
    private final PostService postService;

    public PostController(){
        this.mapper = new PostMapper();
        this.labelService = new LabelServiceImpl();
        this.postService = new PostServiceImpl();
    }


    public PostDTO getById(Long id) {
        return mapper.mapToDTO(postService.getById(id));
    }


    public List<PostDTO> getAll() {
        return postService.getAll().stream()
                .map(mapper::mapToDTO)
                .toList();
    }

    public List<PostDTO> getAllWithoutWriter() {
        return postService.getAllWithoutWriter().stream()
                .map(mapper::mapToDTO)
                .toList();
    }


    public List<PostDTO> getNewPosts(int count) {
        return postService.getXNewCreated(count).stream()
                .map(mapper::mapToDTO)
                .toList();
    }

    public PostDTO create(String content, List<Long> labelsIdForNewPost) {
        List<LabelEntity> selectedLabelEntities = labelService.getSelectedLabelByIdForPost(labelsIdForNewPost);
        return mapper.mapToDTO(postService.create(new PostEntity(content, selectedLabelEntities)));
    }

    public PostDTO update(Long id, String newContent, List<Long> labelsId) {
        PostEntity oldPostEntity = postService.getById(id);
        PostEntity newPostEntity = new PostEntity(id,
                newContent.equals("exit") ? oldPostEntity.getContent() : newContent,
                labelsId.isEmpty() ? Collections.emptyList() : labelService.getSelectedLabelByIdForPost(labelsId));

        return mapper.mapToDTO(postService.update(newPostEntity));
    }


    public void delete(Long id) {
        postService.delete(id);
    }

}
