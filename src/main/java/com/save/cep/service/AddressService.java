package com.save.cep.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.save.cep.DTO.AddressDTO;
import com.save.cep.client.ViaCepClient;
import com.save.cep.model.Address;
import com.save.cep.repository.AddressRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddressService {

    private final ModelMapper mapper;
    private final ViaCepClient viaCep;
    private final AddressRepository addressRepository;
    private final RedisService redisService;

    public AddressDTO insert(String cep){

        cep = this.validateAndFormatCep(cep);

        var redisAddress = redisService.getAdress(cep);

        if (redisAddress == null) {
            var address = this.addressRepository.findById(cep);
            if (address.isEmpty()) {
                redisAddress = viaCep.addressViaCep(cep);
                this.redisService.saveAdress(cep, redisAddress);
                this.addressRepository.save(this.mapper.map(redisAddress, Address.class));
                return redisAddress;
            }  else {
                return this.mapper.map(address, AddressDTO.class);
            }
        }

        return redisAddress;
    }

    private String validateAndFormatCep(String cep) throws RuntimeException {
        if (cep == null || (!cep.matches("\\d{8}") && !cep.matches("\\d{5}-\\d{3}"))) {
            throw new RuntimeException("CEP inválido: " + cep);
        }
        if (cep.matches("\\d{8}")) {
            return cep.substring(0, 5) + "-" + cep.substring(5);
        }
        return cep;
    }
}