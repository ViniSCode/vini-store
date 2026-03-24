package br.com.uninter.vinistore; // Pacote base da aplicação

import org.springframework.boot.SpringApplication; // Importa a classe principal do Spring Boot
import org.springframework.boot.autoconfigure.SpringBootApplication; // Importa a anotação que configura o Spring Boot

@SpringBootApplication // Define esta classe como a configuração principal do projeto e busca componentes no pacote base
public class vinistoreApplication {

	public static void main(String[] args) { // Ponto de entrada (método principal) que inicia a execução de qualquer programa Java
		SpringApplication.run(vinistoreApplication.class, args); // Dá a "partida" no servidor da aplicação Spring Boot
	}

}

