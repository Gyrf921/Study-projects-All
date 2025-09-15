package org.oladushek.repository.impl;

import org.oladushek.config.HibernateConfig;
import org.oladushek.entity.PostEntity;
import org.oladushek.entity.WriterEntity;
import org.oladushek.repository.PostRepository;
import org.oladushek.repository.WriterRepository;

import java.util.List;

public class WriterRepositoryImpl implements WriterRepository {

    @Override
    public WriterEntity findById(Long id) {
        return HibernateConfig.getSessionFactory()
                .fromTransaction(session -> session.find(WriterEntity.class, id));
    }

    @Override
    public List<WriterEntity> findAll() {
        return HibernateConfig.getSessionFactory().fromTransaction(session -> {
            String query = "from WriterEntity w order by w.id desc";
            return session.createSelectionQuery(query, WriterEntity.class)
                    .getResultList();
        });
    }

    @Override
    public WriterEntity save(WriterEntity writerEntity) {
        return HibernateConfig.getSessionFactory().fromTransaction(session -> {
            session.persist(writerEntity);
            return writerEntity;
        });
    }

    @Override
    public WriterEntity update(Long id, WriterEntity writerEntity) {
        return HibernateConfig.getSessionFactory().fromTransaction(session -> {
            WriterEntity writer = session.find(WriterEntity.class, id);
            if (writer == null) {
                System.out.println("PostEntity not found, creating new one");
                session.persist(writerEntity);
                return writerEntity;
            }
            writer.setFirstName(writerEntity.getFirstName());
            writer.setLastName(writerEntity.getLastName());
            writer.getPosts().clear();
            writer.getPosts().addAll(writerEntity.getPosts());
            return writer;
        });
    }

    @Override
    public void deleteById(Long id) {
        HibernateConfig.getSessionFactory().inTransaction(session -> {
            session.remove(session.getReference(WriterEntity.class, id));
        });
    }
}
