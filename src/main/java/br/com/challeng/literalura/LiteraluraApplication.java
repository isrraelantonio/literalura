package br.com.challeng.literalura;

import br.com.challeng.literalura.principal.Principal;
import br.com.challeng.literalura.repository.RepositorioLIvro;
import br.com.challeng.literalura.repository.RepositoryAutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private RepositoryAutor repositorio;
	@Autowired
	private RepositorioLIvro repositorioLivro;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		Principal iniciar = new Principal(repositorio, repositorioLivro);
		iniciar.menu();








	}
}
