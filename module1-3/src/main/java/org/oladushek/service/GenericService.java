package org.oladushek.service;

import java.util.List;

public interface GenericService <T, ID> {
    T getById(ID id);
    List<T> getAll();
    T create(T t);
    T update(T t);
    void delete(Long id);
}
