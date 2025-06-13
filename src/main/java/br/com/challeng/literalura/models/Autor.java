package br.com.challeng.literalura.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
    @Column(unique = true)
     private String autor;
     private Integer nascimento;
     private Integer morte;

     @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
     private List<Livro> livros = new ArrayList<>();

    public Autor() {
    }

    public Autor(DadosAutor autor) {
        this.autor = autor.autor();
        this.nascimento = autor.nascimento();
        this.morte = autor.morte();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Integer getNascimento() {
        return nascimento;
    }

    public void setNascimento(Integer nascimento) {
        this.nascimento = nascimento;
    }

    public Integer getMorte() {
        return morte;
    }

    public void setMorte(Integer morte) {
        this.morte = morte;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(Livro livro) {
        livro.setAutor(this);
        this.livros.add(livro);
    }

    @Override
    public String toString() {
        String titulos = livros.stream()
                .map(Livro::getTitulo) // pega apenas o título de cada livro
                .collect(Collectors.joining(", ")); // junta os títulos separados por vírgula

        return """
            autor = %s
            nascimento = %s
            morte = %s
            livros = [%s]
            """.formatted(autor, nascimento, morte, titulos);
    }
}
