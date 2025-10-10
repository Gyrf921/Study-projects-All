package org.oladushek.service;

import org.oladushek.entity.UserEntity;

import java.util.List;

public interface UserService {
    UserEntity getById(Long userId);

    List<UserEntity> getByAll();

    UserEntity create(String userName);

    UserEntity update(Long userId, String userName);

    void delete(Long userId);
}
