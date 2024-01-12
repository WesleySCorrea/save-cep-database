package com.save.cep.controller;

import com.save.cep.DTO.AddressDTO;
import com.save.cep.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/v1/address")
public class AddressController {

    private final AddressService addressService;
    @PostMapping
    public ResponseEntity<AddressDTO> insert(@RequestHeader(value = "cep") String cep){
        var address = addressService.insert(cep);
        return ResponseEntity.ok().body(address);
    }
}
