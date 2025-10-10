package org.oladushek.service.impl;

import org.oladushek.config.HibernateConfig;
import org.oladushek.entity.UserEntity;
import org.oladushek.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {


    @Override
    public UserEntity getById(Long userId) {
        return HibernateConfig.getSessionFactory()
                .fromTransaction(session ->  session.find(UserEntity.class, userId));
    }

    @Override
    public List<UserEntity> getByAll() {
        return HibernateConfig.getSessionFactory()
                .fromTransaction(session ->
                        session.createSelectionQuery("from UserEntity", UserEntity.class).list());
    }

    @Override
    public UserEntity create(String userName) {
        return HibernateConfig.getSessionFactory().fromTransaction(session -> {
            UserEntity userForSave = new UserEntity(userName);
            session.persist(userForSave);
            return userForSave;
        });

    }

    @Override
    public UserEntity update(Long userId, String userName) {
        return HibernateConfig.getSessionFactory().fromTransaction(session -> {
            UserEntity userForUpdate = session.find(UserEntity.class, userId);
            userForUpdate.setName(userName);
            session.persist(userForUpdate);
            return userForUpdate;
        });

    }


    @Override
    public void delete(Long userId) {
        HibernateConfig.getSessionFactory()
                .inTransaction(session ->
                        session.remove(session.getReference(UserEntity.class, userId)));
    }
}
