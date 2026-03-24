package br.com.uninter.vinistore.model; // Pacote da camada de Modelo do MVC

import jakarta.persistence.Entity; // Importa as as ferramentas necessárias do JPA
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal; // Importação da estrutura BigDecimal que tem precisão absoluta pra dinheiro

@Entity // Informa ao Spring Data que esta classe é uma tabela de Produtos e vai pro Banco
public class Produto {

    @Id // Marcamos id como chave primaria no banco de dados
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Deixa que o banco de dados defina os IDS na ordem de cadastro (1, 2, 3...)
    private Long id; // Identificação numerica única

    private String nome; // Tipo String pois o nome traz letras, números ou caracteres

    private BigDecimal preco; // Recomenda-se usar SEMPRE BigDecimal hoje em dia no Java para trabalhar com Moedas/R$

    private Boolean estoque; // Tipo Boolean quer dizer Verdadeiro ou Falso. Pra saber se tem (true) ou acabou (false).

    public Produto() { 
        // Obrigatório do Hibernate 
    }

    public Produto(String nome, BigDecimal preco, Boolean estoque) { 
        // Construtor parametrizado que permite criar o produto em uma única linha ao invés de vários Sets
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
    }

    // Abaixo iniciam todos os Getters e Setters, seguindo os padrões do Encapsulamento da Programação Orientada a Objetos 
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Boolean getEstoque() {
        return estoque;
    }

    public void setEstoque(Boolean estoque) {
        this.estoque = estoque;
    }
}

