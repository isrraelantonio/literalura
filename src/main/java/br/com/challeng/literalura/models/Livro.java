package br.com.challeng.literalura.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    @Column(unique = true)
    private String titulo;
    private Integer download;
    private String lingua;
    @ManyToOne()
    @JoinColumn(name = "autor_id")
    private Autor  autor;


    public Livro() {
    }

    public Livro(DadosLivro dadosLivro) {
        this.titulo = dadosLivro.titulo();
        this.download = Integer.valueOf(dadosLivro.download());
        this.lingua = dadosLivro.lingua();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getDownload() {
        return download;
    }

    public void setDownload(Integer download) {
        this.download = download;
    }

    public String getLingua() {
        return lingua;
    }

    public void setLingua(String lingua) {
        this.lingua = lingua;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {

        this.autor = autor;
    }

    @Override
    public String toString() {
        return """     
                ------------------------
                titulo = %s
                download = %s
                lingua = %s
                autor = %s
                -------------------------
                """.formatted(titulo, download, lingua, autor.getAutor());
    }
}
