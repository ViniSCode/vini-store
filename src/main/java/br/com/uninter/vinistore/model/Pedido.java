package br.com.uninter.vinistore.model; // Pacote das classes refletidas como tabela

import jakarta.persistence.Entity; // Anota como uma tabela no banco gerada pelo JPA
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity // Isto é fundamental. Sem a anotação @Entity, o Spring Boot ignora e o banco de dados não cria a tabela Pedido
public class Pedido {

    @Id // Marca o atributo principal, o ID que não se repete
    @GeneratedValue(strategy = GenerationType.IDENTITY) // O id ganha auto incremento gerado diretamente pelo banco (H2, MySQL)
    private Long id; // Representa o recivo / numero do Pedido

    private Long clienteId; // Vamos apenas pedir o id numerico de quem está comprando para atrelarmos à venda

    private Long produtoId; // Apenas o numero do id do pão (produto) que a pessoa quer no pedido

    private Integer quantidade; // Integer lida melhor com valores inteiros padrão (1 pão, 5 pães)

    public Pedido() {
        // Construtor vazio que o banco de dados exige por conta dos padrões de reflexão para extração
    }

    public Pedido(Long clienteId, Long produtoId, Integer quantidade) {
        // Este ajuda a instanciar se quisermos popular a tabela facilmente
        this.clienteId = clienteId;
        this.produtoId = produtoId;
        this.quantidade = quantidade;
    }

    // Parte rotineira: todos os Getters pra gente conseguir pegar as variáveis privadas, e Setters para alterar os valores ali dentro
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}

