package org.oladushek.controller;

import lombok.AllArgsConstructor;
import org.oladushek.controller.dto.PostDTO;
import org.oladushek.controller.dto.mapper.PostMapper;
import org.oladushek.model.entity.LabelEntity;
import org.oladushek.model.entity.PostEntity;
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

    public List<PostDTO> getAllByTitle(String title) {
        return postService.getPostsByTitle(title).stream()
                .map(mapper::mapToDTO)
                .toList();
    }

    public PostDTO create(String title, String content, List<Long> labelsIdForNewPost) {
        List<LabelEntity> selectedLabelEntities = getSelectedLabelByIdForPost(labelsIdForNewPost);
        return mapper.mapToDTO(postService.create(new PostEntity(title, content, selectedLabelEntities)));
    }

    public void delete(Long id) {
        postService.delete(id);
    }

    public PostDTO update(Long id, String newTitle, String newContent, List<Long> labelsId) {
        PostEntity oldPostEntity = postService.getById(id);
        PostEntity newPostEntity = new PostEntity(id,
                newTitle.equals("exit") ? oldPostEntity.getTitle() : newTitle,
                newContent.equals("exit") ? oldPostEntity.getContent() : newContent,
                labelsId.isEmpty() ? Collections.emptyList() : getSelectedLabelByIdForPost(labelsId));

        return mapper.mapToDTO(postService.update(newPostEntity));
    }

    private List<LabelEntity> getSelectedLabelByIdForPost(List<Long> labelsIdForNewPost) {
        Predicate<LabelEntity> isSelected = (label) -> labelsIdForNewPost.stream()
                .filter(selectedId -> label.getId().equals(selectedId))
                .findAny().orElse(null) != null;

        return labelService.getAll().stream().filter(isSelected).toList();
    }
}
