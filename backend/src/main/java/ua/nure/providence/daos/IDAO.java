package ua.nure.providence.daos;

import org.springframework.stereotype.Repository;

@Repository
public interface IDAO<T> {
    void insert(T t);
    void update (T t);
    void delete(T t);
    T get(String uuid);
}
