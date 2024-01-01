package com.save.cep.service;

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
    private final ViaCep viaCep;
    private final EnderecoRepository enderecoRepository;

    public EnderecoDTO insert(String cep){

        var enderecoViaCep = viaCep.enderecoViaCep(cep);

        mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

        EnderecoDTO enderecoDtoViaCep = new EnderecoDTO();

        mapper.map(enderecoViaCep, enderecoDtoViaCep);

        var request = mapper.map(enderecoDtoViaCep, Endereco.class);

        var response = enderecoRepository.save(request);

        return mapper.map(response, EnderecoDTO.class);
    }
}
