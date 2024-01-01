package com.save.cep.controller;

import com.save.cep.DTO.EnderecoDTO;
import com.save.cep.service.EnderecoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/v1/enderecos")
public class EnderecoController {

    private final EnderecoService enderecoService;
    @PostMapping
    public ResponseEntity<EnderecoDTO> insert(@RequestBody String cep){

        var endereco = enderecoService.insert(cep);

        return ResponseEntity.ok().body(endereco);
    }
}
