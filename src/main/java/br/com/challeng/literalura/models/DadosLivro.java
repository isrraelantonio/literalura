package br.com.challeng.literalura.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivro(
        @JsonAlias("titulo") String titulo,
        @JsonAlias("download") String download,
        @JsonAlias("lingua") String lingua,
        @JsonAlias("autor") String autor



){

}


