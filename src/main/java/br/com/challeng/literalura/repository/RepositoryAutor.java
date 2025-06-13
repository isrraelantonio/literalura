package br.com.challeng.literalura.repository;

import br.com.challeng.literalura.models.Autor;
import br.com.challeng.literalura.models.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RepositoryAutor extends JpaRepository<Autor, Long> {


    Optional<Autor> findByAutorContainingIgnoreCase(String nomeAutor);

    //@Query("SELECT a FROM Autor a JOIN a.livros l WHERE LOWER(l.titulo) LIKE LOWER(CONCAT('%', :titulo, '%'))")
    @Query("SELECT a FROM Autor a JOIN a.livros l WHERE LOWER(l.titulo) = LOWER(:titulo)")
    Optional<Autor> findByLivroTituloContainingIgnoreCase(String titulo);

    @Query("select a from Autor a where a.morte >= :vivosEm")
    List<Autor> autoresVivosnessePeriodo(int vivosEm);

    @Query("SELECT a FROM Autor a WHERE LOWER(a.autor) LIKE LOWER(CONCAT('%', :nomeAutor, '%'))")
    //@Query ("SELECT a FROM Autor a WHERE LOWER(a.autor) = LOWER(:nomeAutor)")
    List<Autor> buscarPorAutor(String nomeAutor);
}
