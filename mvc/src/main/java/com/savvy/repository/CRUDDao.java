package com.savvy.repository;

import java.util.List;

public interface CRUDDao<T, ID> {
    T save(T obj);

    List<T> findAll();

    T findById(ID id);
}
