package org.oladushek.repository.impl;

import org.oladushek.config.HibernateConfig;
import org.oladushek.entity.LabelEntity;
import org.oladushek.entity.PostEntity;
import org.oladushek.entity.WriterEntity;
import org.oladushek.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.List;

public class PostRepositoryImpl implements PostRepository {

    @Override
    public List<PostEntity> findXNew(int count) {
        return HibernateConfig.getSessionFactory().fromTransaction(session -> {
            String query = "from PostEntity p order by p.created desc";
            return session.createSelectionQuery(query, PostEntity.class)
                    .setMaxResults(count)
                    .getResultList();
        });
    }


    @Override
    public PostEntity findById(Long id) {
        return HibernateConfig.getSessionFactory()
                .fromTransaction(session -> session.find(PostEntity.class, id));
    }

    @Override
    public List<PostEntity> findAll() {
        return HibernateConfig.getSessionFactory().fromTransaction(session -> {
            String query = "from PostEntity p " +
                    "join fetch p.labels " +
                    "join fetch p.writer order by p.created desc";
            return session.createSelectionQuery(query, PostEntity.class)
                    .getResultList();
        });
    }

    @Override
    public PostEntity save(PostEntity postEntity) {
        return HibernateConfig.getSessionFactory().fromTransaction(session -> {

            WriterEntity managedWriter = session.getReference(WriterEntity.class, postEntity.getWriter().getId());
            postEntity.setWriter(managedWriter);

            if (postEntity.getLabels() != null && !postEntity.getLabels().isEmpty()) {
                List<LabelEntity> managedLabels = postEntity.getLabels().stream()
                        .map(label -> session.find(LabelEntity.class, label.getId()))
                        .toList();
                postEntity.setLabels(managedLabels);
            }
            session.persist(postEntity);
            return postEntity;
        });
    }

    @Override
    public PostEntity update(Long id, PostEntity postEntity) {
        return HibernateConfig.getSessionFactory().fromTransaction(session -> {
            PostEntity existing = session.find(PostEntity.class, id);
            if (existing == null) {
                System.out.println("PostEntity not found, creating new one");
                return save(postEntity);
            }

            existing.setContent(postEntity.getContent());
            existing.setUpdated(LocalDateTime.now());

            existing.getLabels().clear();
            if (postEntity.getLabels() != null) {
                List<LabelEntity> managedLabels = postEntity.getLabels().stream()
                        .map(label -> session.find(LabelEntity.class, label.getId()))
                        .toList();
                existing.getLabels().addAll(managedLabels);
            }
            return existing;
        });
    }

    @Override
    public void deleteById(Long id) {
        HibernateConfig.getSessionFactory().inTransaction(session -> {
            session.remove(session.getReference(PostEntity.class, id));
        });
    }
}
