package com.save.cep.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.save.cep.DTO.AddressDTO;
import com.save.cep.client.ViaCep;
import com.save.cep.model.Address;
import com.save.cep.repository.AddressRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddressService {

    private final ModelMapper mapper;
    private final ObjectMapper objectMapper;
    private final ViaCep viaCep;
    private final AddressRepository addressRepository;
    private final RedisService redisService;

    public AddressDTO insert(String cep){

        var redisAddress = redisService.getAdress(cep);
        if (redisAddress == null) {

            AddressDTO addressViaCep = viaCep.addressViaCep(cep);

            String jsonAddress = null;
            try {
                jsonAddress = objectMapper.writeValueAsString(addressViaCep);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            redisService.saveAdress(cep, jsonAddress);

            var address = mapper.map(addressViaCep, Address.class);

            var response = addressRepository.save(address);

            return mapper.map(response, AddressDTO.class);
        }

        AddressDTO address = null;
        try {
            address = objectMapper.readValue(redisAddress, AddressDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return address;
    }
}