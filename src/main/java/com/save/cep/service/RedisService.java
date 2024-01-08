package com.save.cep.service;

import com.save.cep.DTO.AddressDTO;
import com.save.cep.model.Address;
import com.save.cep.repository.RedisRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RedisService {

    private final RedisRepository redisRepository;

    public AddressDTO saveAdress(String cep, AddressDTO address) {

        redisRepository.save(cep, address);

        return address;
    }

    public AddressDTO getAdress(String cep) {
        return redisRepository.getByKey(cep);
    }
}
