package org.oladushek.service.impl;

import org.oladushek.model.Writer;
import org.oladushek.repository.WriterRepository;
import org.oladushek.repository.impl.WriterRepositoryImpl;
import org.oladushek.service.WriterService;

import java.util.List;

public class WriterServiceImpl implements WriterService {

    private final WriterRepository writerRepository = new WriterRepositoryImpl();

    @Override
    public Writer getById(Long id) {
        return writerRepository.findById(id);
    }

    @Override
    public List<Writer> getAll() {
        return writerRepository.findAll();
    }

    @Override
    public Writer create(Writer writer) {
        return writerRepository.save(writer);
    }

    @Override
    public Writer update(Writer writer) {
        if (writerRepository.findById(writer.getId()) != null) {
            return writerRepository.update(writer);
        }
        else {
            System.out.println("Writer " + writer.getId() + " not found. Try to create new writer.");
            return writerRepository.save(writer);
        }
    }

    @Override
    public void delete(Long id) {
        writerRepository.deleteById(id);
    }
}
