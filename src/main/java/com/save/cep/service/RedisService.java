package com.save.cep.service;

import com.save.cep.repository.RedisRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RedisService {
    private final RedisRepository redisRepository;

    public String saveAdress(String cep, String address) {

        redisRepository.save("cep: " + cep, address);

        return address;
    }

    public String getAdress(String cep) {
        // Lógica de negócios antes de recuperar do Redis, se necessário
        return redisRepository.getByKey("cep: " + cep);
    }
}
