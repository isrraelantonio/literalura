package br.com.challeng.literalura.principal;

import br.com.challeng.literalura.models.*;
import br.com.challeng.literalura.repository.RepositorioLIvro;
import br.com.challeng.literalura.repository.RepositoryAutor;
import br.com.challeng.literalura.service.ConverterDados;
import br.com.challeng.literalura.service.MapeamentoJson;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal{

    private RepositoryAutor repositorio;
    private RepositorioLIvro repositorioLivro;
    private final String ENDERECO = "https://gutendex.com/books/?search=";
    private Scanner  leitura = new Scanner(System.in);

    public Principal(RepositoryAutor repositorio, RepositorioLIvro repositorioLivro) {

        this.repositorio = repositorio;
        this.repositorioLivro = repositorioLivro;

    }

    public void menu() throws JsonProcessingException {

        var opcao = -1;


        while (opcao != 0) {
            var menu = """
                    1 - Buscar por titulo
                    2 - Listar autores registrados
                    3 - Listar livros buscados
                    4 - Buscar por autores vivos referente a ano
                    5 - Listar livros buscados por língua
                    6 - Top 5 livros mais baixados
                    7 - Buscar por  autor
                    0 - Sair   
                    
                    DIGITE A OPÇÃO DESEJADA                              
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarlivroWeb();
                    break;
                case 2:
                    listarAutoresDoBanco();
                    break;
                case 3:
                    listarLivrosBuscados();
                    break;
                case 4:
                    buscarAutoresPorAno();
                    break;
                case 5:
                    buscarLivrosPorLingua();
                    break;
                case 6:
                    buscarTop5LivrosMaisBaixados();
                    break;
                case 7:
                    buscarPorAutor();
                    break;


                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }


    private void buscarlivroWeb() {
        System.out.println("digite o titulo do livro");
        String livroBuscado = leitura.nextLine().replace(" ", "+");


        // Obtendo o um json remapeado em um sumário de informações
        MapeamentoJson novoJson = new MapeamentoJson();
        ConverterDados dados = new ConverterDados();
        String sumario = "";

        try {
             sumario = novoJson.obterNovoJson(ENDERECO+livroBuscado);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

            // criação das classes autor e livro servirão de base para procura de registros no banco
        try {

            var autor = dados.obterDados(sumario, DadosAutor.class);
            var autro1 = new Autor(autor);

            var livro = dados.obterDados(sumario, DadosLivro.class);
            var livro1 = new Livro(livro);

            // busca do autor no banco

            Optional<Autor> nomeDoautor = repositorio.findByAutorContainingIgnoreCase(autro1.getAutor());

            // condição para registro do autor

            if (nomeDoautor.isPresent()) {
                System.out.println(autro1);

            } else {
                repositorio.save(autro1);
                System.out.println(autro1);
            }

            // busca do livro no banco de dados
          Optional<Livro> livrobanco = repositorioLivro.findByTituloContainingIgnoreCase(livro1.getTitulo());

            // condição para registo no banco
            if (livrobanco.isPresent()) {
                System.out.println(livro1);
            } else {
                
                Autor autorBuscado = nomeDoautor.orElse(autro1); // caso ainda não tenha sido salvo
                // Define o relacionamento nos dois sentidos
                livro1.setAutor(autorBuscado);                   // relaciona o autor ao livro
                autorBuscado.getLivros().add(livro1);            // adiciona o livro na lista do autor
                repositorio.save(autorBuscado);
                System.out.println(livro1);

            }

        } catch (Exception e) {
            System.out.println("Titulo não encontarado");
        }


    }

    private  void listarAutoresDoBanco(){
        List<Autor> autoresDoBanco = repositorio.findAll();
        autoresDoBanco.stream()
                .forEach(System.out::println);
    }


    private void listarLivrosBuscados(){
        List<Livro> livrosDoBanco = repositorioLivro.findAll();
        livrosDoBanco.stream()
                .forEach(System.out::println);
    }


    private  void  buscarAutoresPorAno(){

        System.out.println("digite o ano para autores vivos no ano especificado.");
        int anoAutor = leitura.nextInt();
        leitura.nextLine();
        List<Autor> autoresVivos = repositorio.autoresVivosnessePeriodo(anoAutor);
        autoresVivos.stream()
                .forEach(System.out::println);
    }


    private void buscarLivrosPorLingua(){

        var menu = """
                   digite a lingua desejada.
                    pt -  português.
                    en -  ingles.
                    es -  espanhol.
                                                 
                    """;

        System.out.println(menu);
        String linguaLivro = leitura.nextLine();
        List<Livro> livrosPorLinuga = repositorioLivro.buscarLivroPorLingua(linguaLivro);
        if (livrosPorLinuga.isEmpty()){
            System.out.println("nehum livro com esse idioma foi registrado.");
        }else {
            livrosPorLinuga.stream()
                    .forEach(System.out::println);
        }
    }


    private  void buscarTop5LivrosMaisBaixados(){

        List<Livro> livrosDoBanco = repositorioLivro.top5Livrosmaisbaixados();
        livrosDoBanco.stream()
                .forEach(System.out::println);


    }

    public void buscarPorAutor(){
        System.out.println("Digite o nome do autor");

       try{
            String nomeAutor = leitura.nextLine();
            List<Autor> nomeDoautor = repositorio.buscarPorAutor(nomeAutor);
            nomeDoautor.stream()
                    .forEach(System.out::println);

        }catch (Exception e){
            System.out.println("Digite um nome válido");
        }


    }
}
