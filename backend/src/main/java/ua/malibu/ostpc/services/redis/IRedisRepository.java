package ua.malibu.ostpc.services.redis;

import org.springframework.stereotype.Repository;

@Repository
public interface IRedisRepository<K, V> {
    V get(K key);
    void insert(K key, V value);
    void delete(K key);
    void refreshExpirationTime(K key);
}
