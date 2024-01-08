package com.save.cep.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.save.cep.DTO.AddressDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class ViaCepClient {

    private final WebClient webClient;

    public ViaCepClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://viacep.com.br/ws").build();
    }
    public AddressDTO addressViaCep(String cep) {
        return webClient.get()
                .uri("/{cep}/json", cep)
                .retrieve()
                .bodyToMono(AddressDTO.class)
                .block();
    }

}
