package org.oladushek.service;

import org.oladushek.entity.LabelEntity;

import java.util.List;

public interface GenericService<T, ID> {
    T getById(ID id);
    List<T> getAll();
    T create(T t);
    T update(ID id, T t);
    void delete(Long id);
}
