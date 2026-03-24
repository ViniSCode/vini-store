package br.com.uninter.vinistore.repository; // Pacote da camada Repository do Padrão Spring // MVC/Respository

import br.com.uninter.vinistore.model.Cliente; // Importando o modelo no qual esse repositório vai lidar
import org.springframework.data.jpa.repository.JpaRepository; // Classe vital para abstrair as consultas (SQL)
import org.springframework.stereotype.Repository; // A anotação pro Spring saber que este é um módulo de acesso 

@Repository // Cria os metadados dessa classe para atuar gerenciando a tabela de Clientes
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Note que isto é uma Interface e herda de JpaRepository (passando a classe principal "Cliente", e seu tipo de ID primario, que é o "Long")
    // Magica do JPA: Você NÃO PRECISA programar os Selects, Updates, Deletes, o Spring implementa sozinho só por herdar do JPA os padrões!
}

