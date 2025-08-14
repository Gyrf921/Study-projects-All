package org.oladushek.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.oladushek.entity.LabelEntity;
import org.oladushek.entity.PostEntity;
import org.oladushek.repository.PostRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl postService;

    @Test
    void getById_shouldReturnPost() {
        PostEntity post = new PostEntity(1L, "Title", new ArrayList<>());
        when(postRepository.findById(1L)).thenReturn(post);

        PostEntity result = postService.getById(1L);

        assertEquals(post, result);
        verify(postRepository).findById(1L);
    }

    @Test
    void getAll_shouldReturnListOfPosts() {
        List<PostEntity> posts = List.of(
                new PostEntity(1L, "Title1", new ArrayList<>()),
                new PostEntity(2L, "Title2", new ArrayList<>(List.of(new LabelEntity(1L, "Label1"))))
        );
        when(postRepository.findAll()).thenReturn(posts);

        List<PostEntity> result = postService.getAll();

        assertEquals(2, result.size());
        assertEquals(posts, result);
        verify(postRepository).findAll();
    }

    @Test
    void getXNewCreated_shouldReturnListOfPosts() {
        List<PostEntity> posts = List.of(new PostEntity(1L, "Title", new ArrayList<>(List.of(new LabelEntity(1L, "Label1")))));
        when(postRepository.findXNew(5)).thenReturn(posts);

        List<PostEntity> result = postService.getXNewCreated(5);

        assertEquals(posts, result);
        verify(postRepository).findXNew(5);
    }

    @Test
    void create_shouldSavePost() {
        PostEntity newPost = new PostEntity(null, "New", new ArrayList<>(List.of(new LabelEntity(1L, "Label1"))));
        PostEntity savedPost = new PostEntity(1L, "New", new ArrayList<>(List.of(new LabelEntity(1L, "Label1"))));

        when(postRepository.save(newPost)).thenReturn(savedPost);

        PostEntity result = postService.create(newPost);

        assertEquals(savedPost, result);
        verify(postRepository).save(newPost);
    }

    @Test
    void update_shouldUpdateIfExists() {
        PostEntity post = new PostEntity(1L, "Old", new ArrayList<>(List.of(new LabelEntity(1L, "Label1"))));
        PostEntity updated = new PostEntity(1L, "Updated", new ArrayList<>(List.of(new LabelEntity(1L, "Label1"))));

        when(postRepository.findById(1L)).thenReturn(post);
        when(postRepository.update(updated)).thenReturn(updated);

        PostEntity result = postService.update(updated);

        assertEquals(updated, result);
        verify(postRepository).findById(1L);
        verify(postRepository).update(updated);
        verify(postRepository, never()).save(updated);
    }

    @Test
    void update_shouldSaveIfNotExists() {
        PostEntity newPost = new PostEntity(2L, "New", new ArrayList<>(List.of(new LabelEntity(1L, "Label1"))));

        when(postRepository.findById(2L)).thenReturn(null);
        when(postRepository.save(newPost)).thenReturn(newPost);

        PostEntity result = postService.update(newPost);

        assertEquals(newPost, result);
        verify(postRepository).findById(2L);
        verify(postRepository).save(newPost);
        verify(postRepository, never()).update(newPost);
    }

    @Test
    void delete_shouldCallDeleteById() {
        postService.delete(3L);

        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        verify(postRepository).deleteById(captor.capture()); //перехватывает фактические аргументы, по сути тут не нужен, но инструмент полезный
        assertEquals(3L, captor.getValue());
    }
}