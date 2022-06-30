package org.cars.services;

import java.util.List;

public interface ServiceInterface <T> {
    List<T> getAll();

    T getById(long id);

    List<T> getByName(String name);

    void saveOrUpdate(T model);

}
