package org.oladushek.service.impl;

import lombok.AllArgsConstructor;
import org.oladushek.entity.WriterEntity;
import org.oladushek.repository.WriterRepository;
import org.oladushek.repository.impl.WriterRepositoryImpl;
import org.oladushek.service.WriterService;

import java.util.List;

public class WriterServiceImpl implements WriterService {

    private final WriterRepository writerRepository;

    public WriterServiceImpl(WriterRepository writerRepository) {
        this.writerRepository = writerRepository;
    }
    public WriterServiceImpl(){
        writerRepository = new WriterRepositoryImpl();
    }

    @Override
    public WriterEntity getById(Long id) {
        return writerRepository.findById(id);
    }

    @Override
    public List<WriterEntity> getAll() {
        return writerRepository.findAll();
    }

    @Override
    public WriterEntity create(WriterEntity writerEntity) {
        return writerRepository.save(writerEntity);
    }

    @Override
    public WriterEntity update(WriterEntity writerEntity) {
        if (writerRepository.findById(writerEntity.getId()) != null) {
            return writerRepository.update(writerEntity);
        }
        else {
            System.out.println("Writer " + writerEntity.getId() + " not found. Try to create new writer.");
            return writerRepository.save(writerEntity);
        }
    }

    @Override
    public void delete(Long id) {
        writerRepository.deleteById(id);
    }
}
