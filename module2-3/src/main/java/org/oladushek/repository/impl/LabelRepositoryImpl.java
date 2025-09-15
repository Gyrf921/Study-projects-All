package org.oladushek.repository.impl;


import org.oladushek.config.HibernateConfig;
import org.oladushek.entity.LabelEntity;
import org.oladushek.repository.LabelRepository;

import java.util.List;



public class LabelRepositoryImpl implements LabelRepository {

    @Override
    public LabelEntity findByName(String name) {
        return HibernateConfig.getSessionFactory().fromTransaction(session -> {
            String query = "from LabelEntity where name like ?1";
            return session.createSelectionQuery(query, LabelEntity.class)
                    .setParameter(1, name)
                    .getSingleResult();
        });
    }

    @Override
    public LabelEntity findById(Long id) {
        return HibernateConfig.getSessionFactory()
                .fromTransaction(session -> session.find(LabelEntity.class, id));
    }

    @Override
    public List<LabelEntity> findAll() { //Возможно стоило бы ограничить Page'ами, но пока не будем
        return HibernateConfig.getSessionFactory().fromTransaction(session -> {
            String query = "from LabelEntity";
            return session.createSelectionQuery(query, LabelEntity.class)
                    .getResultList();
        });
    }

    @Override
    public LabelEntity save(LabelEntity labelEntity) {
        return HibernateConfig.getSessionFactory().fromTransaction(session -> {
            session.persist(labelEntity);
            return labelEntity;
        });
    }

    @Override
    public LabelEntity update(Long aLong, LabelEntity labelEntity) {
        return HibernateConfig.getSessionFactory().fromTransaction(session -> {
            LabelEntity labelForUpdate = session.find(LabelEntity.class, aLong);
            if (labelForUpdate == null) {
                System.out.println("LabelEntity not found, creating new one");
                session.persist(labelEntity);
                return labelEntity;
            }
            labelForUpdate.setName(labelEntity.getName());
            return labelForUpdate;
        });
    }


    @Override
    public void deleteById(Long id) {
        HibernateConfig.getSessionFactory().inTransaction(session -> {
            session.remove(session.getReference(LabelEntity.class, id));
        });
    }
}
