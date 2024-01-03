package com.save.cep.service;

import com.save.cep.DTO.EnderecoDTO;
import com.save.cep.repository.RedisRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RedisService {
    private final RedisRepository redisRepository;

    public String saveAdress(String cep, String endereco) {

        redisRepository.save("cep: " + cep, endereco);

        return endereco;
    }

    public String getAdress(String cep) {
        // Lógica de negócios antes de recuperar do Redis, se necessário
        return redisRepository.getByKey("cep: " + cep);
    }
}
