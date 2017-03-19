package ua.nure.providence.utils.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nure.providence.services.redis.IRedisRepository;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

@Service
public class TokenGenerator {
    @Autowired
    private IRedisRepository redisRepository;
    private Random random = new SecureRandom();

    public String issueToken(String uuid) {
        String tokenValue = new BigInteger(130, random).toString(32);
        redisRepository.insert(tokenValue, uuid);
        return tokenValue;
    }

    public IRedisRepository getRedisRepository() {
        return redisRepository;
    }

    public void setRedisRepository(IRedisRepository redisRepository) {
        this.redisRepository = redisRepository;
    }
}
