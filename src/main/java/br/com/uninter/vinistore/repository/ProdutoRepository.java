package br.com.uninter.vinistore.repository; // Pacote isolado com repositórios para comunicação limpa

import br.com.uninter.vinistore.model.Produto; // Define em qual Classe Modelo essa interface atua
import org.springframework.data.jpa.repository.JpaRepository; // Traz de presente todos os metodos findAll, save, delete, create.
import org.springframework.stereotype.Repository; // Permite gerenciar essa entidade inteira por meio do Spring

@Repository // Sem essa anotação, não vira um objeto Spring ou interface DAO e os controllers podem não achá-lo
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    // Vazio mesmo! O Spring cria comandos Select, Delete e Insert SQL no momento que compilamos o programa baseado na entidade ali declarada
}

