package org.oladushek.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.oladushek.entity.LabelEntity;
import org.oladushek.repository.LabelRepository;
import org.oladushek.repository.impl.LabelRepositoryImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
class LabelServiceImplTest {

    @Mock
    private LabelRepository labelRepository;

    @InjectMocks
    private LabelServiceImpl service;

    @Test
    void whenMethodGetById_thenCorrect() {
        Mockito.when(labelRepository.findById(anyLong())).thenReturn(new LabelEntity(1L, "test"));

        LabelEntity lb = service.getById(1L);

        assertEquals(1L, lb.getId());
        assertEquals("test", lb.getName());
    }

    @Test
    void whenMethodGetAll_thenCorrect() {
        Mockito.when(labelRepository.findAll()).thenReturn(List.of(new LabelEntity(1L, "test"), new LabelEntity(2L, "test")));

        List<LabelEntity> lb = service.getAll();

        assertEquals(2L, lb.size());
        assertEquals("test", lb.getFirst().getName());
    }

    @Test
    void whenMethodCreate_thenCorrect() {
        Mockito.when(labelRepository.save(any(LabelEntity.class))).thenReturn(new LabelEntity(1L, "test"));

        LabelEntity lb = service.create(new LabelEntity("test"));

        assertEquals("test", lb.getName());
    }

    @Test
    void whenMethodUpdate_thenCorrect() {
        Mockito.when(labelRepository.findById(any(Long.class))).thenReturn(new LabelEntity(2L, "test"));
        Mockito.when(labelRepository.update(any(LabelEntity.class))).thenReturn(new LabelEntity(2L, "test"));

        LabelEntity lb = service.update(new LabelEntity(2L, "test"));

        assertEquals(2L, lb.getId());
        assertEquals("test", lb.getName());
    }

    @Test
    void whenMethodUpdate_thenWrong() {
        Mockito.when(labelRepository.findById(any(Long.class))).thenReturn(null);
        Mockito.when(labelRepository.save(any(LabelEntity.class))).thenReturn(new LabelEntity(1L, "test"));

        LabelEntity lb = service.update(new LabelEntity(2L, "test"));

        assertEquals(1L, lb.getId());
        assertEquals("test", lb.getName());
    }

}