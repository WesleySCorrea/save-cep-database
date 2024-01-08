package com.save.cep.repository;

import com.save.cep.DTO.AddressDTO;
import com.save.cep.model.Address;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

@Repository
public class RedisRepository {

    private final RedisTemplate<String, AddressDTO> redisTemplate;

    public RedisRepository(RedisTemplate<String, AddressDTO> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void save(String key, AddressDTO address) {
       this.redisTemplate.opsForValue().set(key,address);
    }

    public AddressDTO getByKey(String key) {
        return this.redisTemplate.opsForValue().get(key);
    }
}
