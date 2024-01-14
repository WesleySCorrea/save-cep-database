package com.save.cep.service;

import com.save.cep.DTO.AddressDTO;
import com.save.cep.DTO.JsonAddress;
import com.save.cep.client.ViaCepClient;
import com.save.cep.exception.NotFoundCepException;
import com.save.cep.rds.AddressService;
import com.save.cep.redis.RedisService;
import com.save.cep.sqs.SqsMessagePublisherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final RedisService redisService;
    private final AddressService addressService;
    private final ViaCepClient viaCepClient;
    private final SqsMessagePublisherService sqsMessagePublisherService;

    public AddressDTO getCep(String cep){
        log.info("Starting TaskService  getCep: "+ cep);
        cep = this.validateAndFormatCep(cep);
        var redisAddress = this.redisService.getAdress(cep);

        if (redisAddress == null) {
            var address = this.addressService.findById(cep);
            if (address == null) {
                redisAddress = this.viaCepClient.addressViaCep(cep);
                if(redisAddress.getLocalidade() == null){
                    log.error("CEP Not Found on Via CEP API: "+ cep);
                    throw new NotFoundCepException("CEP NÃO ENCONTRADO: "+cep);
                }
                this.sqsMessagePublisherService.sendMessage(JsonAddress.toJson(redisAddress));
                this.redisService.saveAdress(cep, redisAddress);
                return redisAddress;
            }  else {
                return address;
            }
        }
        return redisAddress;
    }

    private String validateAndFormatCep(String cep) throws RuntimeException {
        log.info("Starting validate Cep: "+ cep);
        if (cep == null || (!cep.matches("\\d{8}") && !cep.matches("\\d{5}-\\d{3}"))) {
            log.error("CEP INVÁLIDO: " + cep);
            throw new NotFoundCepException("CEP INVÁLIDO: " + cep);
        }
        if (cep.matches("\\d{8}")) {
            var cepWith = cep.substring(0, 5) + "-" + cep.substring(5);
            log.info("Returning validate Cep: "+ cepWith);
            return cepWith;
        }
        log.info("Returning validate Cep: "+ cep);
        return cep;
    }
}
