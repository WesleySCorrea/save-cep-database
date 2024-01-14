package com.save.cep.redis;

import com.save.cep.DTO.AddressDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class RedisService {

    private final RedisTemplate<String, AddressDTO> redisTemplate;

    public void saveAdress(String cep, AddressDTO address) {
        log.info("REDIS - Starting save object on REDIS: "+ address.toString());
        redisTemplate.opsForValue().set(cep, address);
        log.info("REDIS - Finishing save object on REDIS: "+ address.toString());
    }

    public AddressDTO getAdress(String cep) {
        log.info("REDIS - Starting get  from REDIS: "+ cep);
        var redisObject =  redisTemplate.opsForValue().get(cep);
        log.info("REDIS - Finishing get  from REDIS: "+ redisObject);
        return redisObject;
    }
}
