package com.save.cep.client;

import com.save.cep.DTO.AddressDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
public class ViaCepClient {

    private final WebClient webClient;

    public ViaCepClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://viacep.com.br/ws").build();
    }
    public AddressDTO addressViaCep(String cep) {
        log.info("Starting HTTP Request on API ViaCEP on CEP: " + cep);
        return webClient.get()
                .uri("/{cep}/json", cep)
                .retrieve()
                .bodyToMono(AddressDTO.class)
                .block();
    }

}
