package org.example.store;

import java.util.List;

public interface Store<T, ID> {
    List<T> getAll();

    void add(T obj);

    T update(T obj);

    void delete(ID id);
}
