package br.com.uninter.vinistore.model; // Define o pacote responsável por guardar todos os modelos/entidades do banco

import jakarta.persistence.Entity; // Anotação para marcar que a classe vai virar uma tabela no banco de dados
import jakarta.persistence.GeneratedValue; // Anotação responsável por gerar chaves de forma automática
import jakarta.persistence.GenerationType; // Define a estratégia da geração de chave primaria
import jakarta.persistence.Id; // Informa qual será a chave primária
import java.time.LocalDate; // Importa formato nativo para armazenar apenas as datas (ano-mes-dia)

@Entity // Representa que esta classe é uma tabela chamada "Cliente" no banco de dados relacional
public class Cliente {

    @Id // Marca este campo (id) como sendo a chave primária (exclusiva) na tabela
    @GeneratedValue(strategy = GenerationType.IDENTITY) // O campo id terá AUTO_INCREMENT (gera 1, 2, 3 automático no banco)
    private Long id; // Variável tipo Long responsável pelo ID único do Cliente

    private String nome; // Campo tipo Texto que guardará o Nome do cliente

    private LocalDate clienteDesde; // Campo para anotar quando esse cliente criou perfil/fez a 1ª compra

    public Cliente() { 
        // Construtor vazio. É totalmente OBRIGATÓRIO (exigência do JPA/Hibernate) para permitir instanciar dados do banco.
    }

    public Cliente(String nome, LocalDate clienteDesde) { 
        // Construtor com campos. Usado pelos desenvolvedores para preencher rápido um objeto cliente na memória.
        this.nome = nome;
        this.clienteDesde = clienteDesde;
    }

    // Getters and Setters: Como os atributos (nome, id) são privados, precisamos destes métodos públicos 
    // para recuperar valores (get) e injetar/mudar valores (set) nas variáveis, uma boa prática de Encapsulamento.

    public Long getId() { // Busca o id armazenado
        return id;
    }

    public void setId(Long id) { // Define e altera o ID desta classe
        this.id = id;
    }

    public String getNome() { // Puxa/Lê o valor de Nome gravado
        return nome;
    }

    public void setNome(String nome) { // Altera a variável nome com o valor que passamos entre os parêntesis
        this.nome = nome;
    }

    public LocalDate getClienteDesde() { // Puxa a data
        return clienteDesde;
    }

    public void setClienteDesde(LocalDate clienteDesde) { // Define a data
        this.clienteDesde = clienteDesde;
    }
}

