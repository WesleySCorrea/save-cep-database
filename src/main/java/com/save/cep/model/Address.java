package com.save.cep.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "address")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Address {

    @Id
    @Column(name = "cep")
    private String cep;

    @Column(name = "logradouro")
    private String logradouro;

    @Column(name = "complemento")
    private String complemento;

    @Column(name = "bairro")
    private String bairro;

    @Column(name = "localidade")
    private String localidade;

    @Column(name = "uf")
    private String uf;

    @Column(name = "ibge")
    private String ibge;

    @Column(name = "gia")
    private String gia;

    @Column(name = "ddd")
    private String ddd;

    @Column(name = "siafi")
    private String siafi;
}
