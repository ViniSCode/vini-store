package br.com.uninter.vinistore.repository; // Isola camadas do acesso das classes cruas (Pedido) para repositório (JPA)

import br.com.uninter.vinistore.model.Pedido; // Repositório feito pra operar neste modulo Modelo
import org.springframework.data.jpa.repository.JpaRepository; // Facilita as transações entre Spring e BD Relacional 
import org.springframework.stereotype.Repository; // Para que o Autowired do seu controller encontre esta interface facilmente 

@Repository // Transforma este arquivo na principal ponte de envio e requerimento do Banco de Dados dessa tabela (Pedido)
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    // JPA Repository já lida com todo controle do Entity Pedido e recebe as chaves Long (o Type exigido lá no pedaço do <T, ID>)
}

