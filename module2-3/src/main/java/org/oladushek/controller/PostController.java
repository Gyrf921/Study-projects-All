package org.oladushek.controller;

import lombok.AllArgsConstructor;
import org.oladushek.dto.PostDTO;
import org.oladushek.entity.LabelEntity;
import org.oladushek.entity.PostEntity;
import org.oladushek.entity.WriterEntity;
import org.oladushek.mapper.PostMapper;
import org.oladushek.service.LabelService;
import org.oladushek.service.PostService;
import org.oladushek.service.WriterService;
import org.oladushek.service.impl.LabelServiceImpl;
import org.oladushek.service.impl.PostServiceImpl;
import org.oladushek.service.impl.WriterServiceImpl;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
public class PostController {

    private final PostMapper mapper;
    private final LabelService labelService;
    private final PostService postService;
    private final WriterService writerService;

    public PostController(){
        this.mapper = new PostMapper();
        this.labelService = new LabelServiceImpl();
        this.postService = new PostServiceImpl();
        this.writerService = new WriterServiceImpl();
    }


    public PostDTO getById(Long id) {
        return mapper.mapToDTO(postService.getById(id));
    }


    public List<PostDTO> getAll() {
        return postService.getAll().stream()
                .map(mapper::mapToDTO)
                .toList();
    }


    public List<PostDTO> getNewPosts(int count) {
        return postService.getXNewCreated(count).stream()
                .map(mapper::mapToDTO)
                .toList();
    }

    public PostDTO create(String content, List<Long> labelsIdForNewPost, Long writerId) {
        WriterEntity writer = writerService.getById(writerId);
        if (writer != null) {
            List<LabelEntity> selectedLabelEntities = labelService.getSelectedLabelByIdForPost(labelsIdForNewPost);
            return mapper.mapToDTO(postService.create(new PostEntity(content, writer, selectedLabelEntities)));
        }
        System.out.println("Wrong writerId");
        return null;
    }

    public PostDTO update(Long id, String newContent, List<Long> labelsId) {
        PostEntity oldPostEntity = postService.getById(id);
        PostEntity newPostEntity = new PostEntity(newContent.equals("exit") ? oldPostEntity.getContent() : newContent,
                oldPostEntity.getWriter(),
                labelsId.isEmpty() ? Collections.emptyList() : labelService.getSelectedLabelByIdForPost(labelsId));

        return mapper.mapToDTO(postService.update(id, newPostEntity));
    }


    public void delete(Long id) {
        postService.delete(id);
    }

}
