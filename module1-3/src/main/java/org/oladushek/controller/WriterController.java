package org.oladushek.controller;

import lombok.AllArgsConstructor;
import org.oladushek.controller.dto.WriterDTO;
import org.oladushek.controller.dto.mapper.WriterMapper;
import org.oladushek.model.Post;
import org.oladushek.model.Writer;
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
        return mapper.mapToDTO(writerService.create(new Writer(firstName, lastName, getSelectedPostByIdForWriter(postsId))));
    }

    public WriterDTO createWithoutPosts(String firstName, String lastName) {
        return mapper.mapToDTO(writerService.create(new Writer(firstName, lastName)));
    }

    public void delete(Long id) {
        writerService.delete(id);
    }

    public WriterDTO update(Long id, String firstName, String lastName, List<Long> postsId) {
        Writer oldWriter = writerService.getById(id);
        Writer newWriter = new Writer(id,
                firstName.equals("exit") ? oldWriter.getFirstName() : firstName,
                lastName.equals("exit") ? oldWriter.getLastName() : lastName,
                postsId.isEmpty() ? Collections.emptyList() : getSelectedPostByIdForWriter(postsId));

        return mapper.mapToDTO(writerService.update(newWriter));
    }

    public WriterDTO addNewPost(Long id, Long postId) {
        Writer oldWriter = writerService.getById(id);
        oldWriter.getPosts().add(postService.getById(postId));
        return mapper.mapToDTO(writerService.update(oldWriter));
    }

    private List<Post> getSelectedPostByIdForWriter(List<Long> postsIdForNewWriter) {
        Predicate<Post> isSelected = (post) -> postsIdForNewWriter.stream()
                .filter(selectedId -> post.getId().equals(selectedId))
                .findAny().orElse(null) != null;

        return postService.getAll().stream().filter(isSelected).toList();
    }

}
