package org.oladushek.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.oladushek.entity.WriterEntity;
import org.oladushek.repository.WriterRepository;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WriterServiceImplTest {
    @Mock
    private WriterRepository writerRepository;

    @InjectMocks
    private WriterServiceImpl writerService;


    @Test
    void getById_shouldReturnWriter() {
        WriterEntity writer = new WriterEntity(1L, "John", "Doe", List.of());
        when(writerRepository.findById(1L)).thenReturn(writer);

        WriterEntity result = writerService.getById(1L);

        assertEquals(writer, result);
        verify(writerRepository).findById(1L);
    }

    @Test
    void getAll_shouldReturnListOfWriters() {
        List<WriterEntity> writers = Arrays.asList(
                new WriterEntity(1L, "John", "Doe", List.of()),
                new WriterEntity(2L, "Jane", "Smith", List.of())
        );
        when(writerRepository.findAll()).thenReturn(writers);

        List<WriterEntity> result = writerService.getAll();

        assertEquals(writers, result);
        verify(writerRepository).findAll();
    }

    @Test
    void create_shouldSaveWriter() {
        WriterEntity newWriter = new WriterEntity("John", "Doe", List.of());
        WriterEntity savedWriter = new WriterEntity(1L, "John", "Doe", List.of());

        when(writerRepository.save(newWriter)).thenReturn(savedWriter);

        WriterEntity result = writerService.create(newWriter);

        assertEquals(savedWriter, result);
        verify(writerRepository).save(newWriter);
    }

    @Test
    void update_shouldUpdateIfExists() {
        WriterEntity writer = new WriterEntity(1L, "John", "Doe", List.of());
        WriterEntity updated = new WriterEntity(1L, "John", "Updated", List.of());

        when(writerRepository.findById(1L)).thenReturn(writer);
        when(writerRepository.update(updated)).thenReturn(updated);

        WriterEntity result = writerService.update(updated);

        assertEquals(updated, result);
        verify(writerRepository).findById(1L);
        verify(writerRepository).update(updated);
        verify(writerRepository, never()).save(any());
    }

    @Test
    void update_shouldSaveIfNotExists() {
        WriterEntity newWriter = new WriterEntity(2L, "Jane", "Doe", List.of());

        when(writerRepository.findById(2L)).thenReturn(null);
        when(writerRepository.save(newWriter)).thenReturn(newWriter);

        WriterEntity result = writerService.update(newWriter);

        assertEquals(newWriter, result);
        verify(writerRepository).findById(2L);
        verify(writerRepository).save(newWriter);
        verify(writerRepository, never()).update(any());
    }

    @Test
    void delete_shouldCallDeleteById() {
        writerService.delete(5L);

        verify(writerRepository).deleteById(5L);
        verifyNoMoreInteractions(writerRepository);
    }
}