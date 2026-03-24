package br.com.uninter.vinistore.controller; // Declaração de pacote do padrão MVC para camada Controller

import br.com.uninter.vinistore.model.Pedido; // Entidade Pedido  
import br.com.uninter.vinistore.repository.PedidoRepository; // Repositório que cuida das regras e execução da entidade pedidto 
import org.springframework.beans.factory.annotation.Autowired; // Responsável pela Injeção de Dependências feita pelo Spring DI 
import org.springframework.http.HttpStatus; // Utilos para colocar Http Exatos da documentação e Status Code
import org.springframework.http.ResponseEntity; // Wrapper de resposta onde enviamos e compomos Retorno HTTP + Data do retorno num Pacote.
import org.springframework.web.bind.annotation.*; // Mapeamentos @GetMapping, @DeleteMapping, etc, são fornecidos nesta biblioteca gigante do SpringWeb!

import java.util.List; // Classe Util Lista nativa para trabalhar um leque de entidades (Retorno plural)
import java.util.Optional; // Tratativa padrão contra erros quando podemos retornar nulo da base de dados sem problemas.

@RestController // Diz que ela não vai enviar o Front-end para os usuários, mas sim dados limpos (geralmente JSON) no corpo da Web
@RequestMapping("/pedidos") // Aponta qual vai ser o endereço básico, ou URL route raiz acessada. (http://.../pedidos)
public class PedidoController {

    @Autowired // Transfere a complexa carga de instanciar classes pro Spring. Ele se responsabiliza na injeção. 
    private PedidoRepository pedidoRepository;

    @GetMapping // Requisição tipo Buscar
    public List<Pedido> listarTodos() { 
        return pedidoRepository.findAll(); // Busca e cospe todos pedidos guardados pelo método magico de Jpa "findAll" 
    }

    @GetMapping("/{id}") // O endereço para /pedidos e ganha o parametro de chave de Path /{id} 
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) { // E este Path será sugado à Força para cá 
        Optional<Pedido> pedido = pedidoRepository.findById(id); // Tentativa normal e sem ricos de procurar um registro só 
        return pedido.map(ResponseEntity::ok) // Expressoes limpas lambda: achou? ok nele + Status Code 200 HTTP 
                .orElseGet(() -> ResponseEntity.notFound().build()); // Expressão: não? Not Foud, Build limpo. Status HTTP 404 Error    
    }

    @PostMapping // Tipo criar. Insere novidades.
    @ResponseStatus(HttpStatus.CREATED) // Para as melhores praticas forcar Código HTTP "201 - Create" pro client postman no exito!  
    public Pedido cadastrar(@RequestBody Pedido pedido) { // Transforma corpo web JSON pra Classe Modelo Pedido prontinha pra o Java lidar facilmente 
        return pedidoRepository.save(pedido); // Retorna a salvada. Note que com @RequestBody quem traz a id do front ele despreza  
    }

    @PutMapping("/{id}") // Tipo Alterar total
    public ResponseEntity<Pedido> atualizar(@PathVariable Long id, @RequestBody Pedido pedidoAtualizado) {
        if (!pedidoRepository.existsById(id)) { // Precave apagarmos fantasma se nao tem a chave ali! 
            return ResponseEntity.notFound().build(); // Solta 404  
        }
        pedidoAtualizado.setId(id); // Garante mesmo que o usuario mude a Id dele em form, nós alteramos o certo pela url (pathvariable).
        Pedido salvo = pedidoRepository.save(pedidoAtualizado); // Salva novamente. No Hibernate ao realizar Save num ID de pre-existência o modo Vira "Update SQL". 
        return ResponseEntity.ok(salvo); // Solta um ok cheio de corpo para podermos conferir nossa troca atual !     
    }

    @DeleteMapping("/{id}") // Delete mapping pelo URL e seu parametro. 
    public ResponseEntity<Void> excluir(@PathVariable Long id) { // E retorna o TIPO "Void" pra representar resposta limpa / Oca sem choro após sumir
        if (!pedidoRepository.existsById(id)) { // Confere presença p não tentar estourar SQL!   
            return ResponseEntity.notFound().build(); // Status 404 pra cara caso o id for alucinação da request.
        }
        pedidoRepository.deleteById(id); // A exclusao 
        return ResponseEntity.noContent().build(); // O HTTP da Excluir do Rest indica que sumiu mandando a regra Web -> o 204 No Content e fechar a conexão bonito!
    }
}

