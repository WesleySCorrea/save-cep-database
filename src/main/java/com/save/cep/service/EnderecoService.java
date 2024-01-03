package com.save.cep.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.save.cep.DTO.EnderecoDTO;
import com.save.cep.client.ViaCep;
import com.save.cep.model.Endereco;
import com.save.cep.repository.EnderecoRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EnderecoService {

    private final ModelMapper mapper;
    private final ObjectMapper objectMapper;
    private final ViaCep viaCep;
    private final EnderecoRepository enderecoRepository;
    private final RedisService redisService;

    public EnderecoDTO insert(String cep){

        var redisEndereco = redisService.getAdress(cep);
        if (redisEndereco == null) {

            EnderecoDTO enderecoViaCep = viaCep.enderecoViaCep(cep);

            String jsonEndereco = null;
            try {
                jsonEndereco = objectMapper.writeValueAsString(enderecoViaCep);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            redisService.saveAdress(cep, jsonEndereco);

            var endereco = mapper.map(enderecoViaCep, Endereco.class);

            var response = enderecoRepository.save(endereco);

            return mapper.map(response, EnderecoDTO.class);
        }

        EnderecoDTO endereco = null;
        try {
            endereco = objectMapper.readValue(redisEndereco, EnderecoDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return endereco;
    }
}