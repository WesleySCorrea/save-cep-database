package com.save.cep.repository;

import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

@Repository
public class RedisRepository {

    private final Jedis jedis;

    public RedisRepository() {
        this.jedis = new Jedis("localhost", 6379);
    }

    public void save(String key, String endereco) {
        jedis.set(key, endereco);
    }

    public String getByKey(String key) {
        return jedis.get(key);
    }
}
