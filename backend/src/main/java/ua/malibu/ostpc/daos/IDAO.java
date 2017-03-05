package ua.malibu.ostpc.daos;

import org.springframework.stereotype.Repository;

@Repository
public interface IDAO<T> {
    void insert(T t);
    void update (T t);
    void delete(T t);
    T get(String uuid);
}
