package ua.nure.providence.services.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class RedisRepository implements IRedisRepository<String, String> {
    private RedisTemplate<String, String> redisTemplate;
    private ValueOperations<String, String> valueOps;

    @Autowired
    public RedisRepository(@Qualifier(value="redisTemplate") RedisTemplate<String, String> temp) {
        redisTemplate = temp;
        valueOps = redisTemplate.opsForValue();
    }

    @Override
    public String get(String key) {
        return valueOps.get(key);
    }

    @Override
    public void insert(String key, String value, long time) {
        valueOps.set(key, value, time, TimeUnit.SECONDS);
    }
    @Override
    public void insert(String key, String value) {
        valueOps.set(key, value, 1, TimeUnit.HOURS);
    }

    @Override
    public void delete(String key) {
        valueOps.getOperations().delete(key);
    }

    @Override
    public void refreshExpirationTime(String key) {
        valueOps.getOperations().expire(key, 1, TimeUnit.DAYS);
    }
}
