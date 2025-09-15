package org.oladushek.repository;

import java.util.List;

public interface GenericRepository<T, ID>{
    T findById(ID id);
    List<T> findAll();
    T save(T t);
    T update(ID id, T t);
    void deleteById(ID id);
}
