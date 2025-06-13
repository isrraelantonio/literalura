package br.com.challeng.literalura.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosAutor(
        @JsonAlias("nome") String autor,
        @JsonAlias("nascimento") Integer nascimento,
        @JsonAlias("morte") Integer morte

){

}
