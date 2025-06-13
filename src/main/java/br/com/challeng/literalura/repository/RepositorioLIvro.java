package br.com.challeng.literalura.repository;

import br.com.challeng.literalura.models.Autor;
import br.com.challeng.literalura.models.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RepositorioLIvro extends JpaRepository<Livro, Long> {


    Optional<Livro> findByTituloContainingIgnoreCase(String titulo);

    //@Query("SELECT l FROM Livro l WHERE LOWER(l.titulo) LIKE LOWER(CONCAT('%', :titulo, '%'))")
    @Query("SELECT l FROM Livro l  WHERE LOWER(l.titulo) = LOWER(:titulo)")
    List<Livro> buscarPorTitulo(String titulo);

    @Query("SELECT l FROM Livro l  WHERE LOWER(l.lingua) = LOWER(:lingua)")
    List<Livro> buscarLivroPorLingua(String lingua);

    @Query("SELECT l FROM Livro l ORDER BY l.download DESC LIMIT 5")
    List<Livro> top5Livrosmaisbaixados();
}
