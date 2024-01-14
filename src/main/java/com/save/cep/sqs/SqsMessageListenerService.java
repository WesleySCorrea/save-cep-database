package com.save.cep.sqs;

import com.save.cep.DTO.JsonAddress;
import com.save.cep.rds.AddressService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class SqsMessageListenerService {

    private final AddressService addressService;

    @SqsListener("filaToPersist")
    public void listen(String message) {
        log.info("SQS - Message received: "+message);
        var address = JsonAddress.toAddress(message);
        log.info("SQS - Starting persisted new address on RDS: "+address);
        this.addressService.insert(address);
        log.info("SQS - Finish with success SQS Thread listener: "+address);
    }
}
