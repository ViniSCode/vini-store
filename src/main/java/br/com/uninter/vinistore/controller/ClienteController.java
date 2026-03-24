package br.com.uninter.vinistore.controller; // Camada Controller no padrão MVC

import br.com.uninter.vinistore.model.Cliente; // Precisamos do Molde
import br.com.uninter.vinistore.repository.ClienteRepository; // e das Ações que guardam do molde para o Banco
import org.springframework.beans.factory.annotation.Autowired; // Responsável pela injeção de classes de forma mágica e inteligente
import org.springframework.http.HttpStatus; // Status HTTP para Web e Postman
import org.springframework.http.ResponseEntity; // Auxiliar de Resposta completa (Corpo/Header/Status) para os clientes Request
import org.springframework.web.bind.annotation.*; // Libera tudo relacionado a rotas HTTP e Paths

import java.util.List; // Padrão Colecionador Java que serve pra gente agrupar listas de dados com flexibilidade
import java.util.Optional; // Impede nosso java de quebrar se um cliente der Null / Não Existir e avisa 

@RestController // Diferente de @Controller (que retorna tela/HTML completo), RestController formata e retorna padrão tipo JSON automaticamente
@RequestMapping("/clientes") // Qualquer acesso na URL que comece com "...:8080/clientes" será repassada pra cá
public class ClienteController {

    @Autowired // Auto-Injeção de dependência. Em vez de dizermos rep = new ClienteRepository(), o Spring Injeta pra gente sozinho a classe que o Spring Data construiu!
    private ClienteRepository clienteRepository; 

    @GetMapping // Acessado quando rolar requisição do tipo GET base
    public List<Cliente> listarTodos() { 
        return clienteRepository.findAll(); // findAll() pega todo mundo no BD e nossa função joga pro postman de volta 
    }

    @GetMapping("/{id}") // O endereço se soma virando assim /clientes/2 ou com o Id que escolher na curva
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) { // Anotação @PathVariable manda o ID da URL ali de cima pra variação nativa aqui ("id")
        Optional<Cliente> cliente = clienteRepository.findById(id); // Procura no Banco pela chave mestre. Pode retornar ou não.
        return cliente.map(ResponseEntity::ok) // Se achar no Optional map, retorna Entity Corpo dele e avisa código de internet = 200 (OK)
                .orElseGet(() -> ResponseEntity.notFound().build()); // Caso de der match ou Optional nulo, o Status cai pra .notFound (404) sumindo de boa
    }

    @PostMapping // Quando rolar o Verbo POST via POSTMAN (Inclusão e Novos Modelos criados) no /clientes
    @ResponseStatus(HttpStatus.CREATED) // Faz sucesso voltar com Codigo HTTP bonito que é exatamente 201
    public Cliente cadastrar(@RequestBody Cliente cliente) { // A chave request body faz um desmenbramento do que passamos de JSON no postam no molde do classe Java Cliente já construída  
        return clienteRepository.save(cliente); // Executa um insert no banco e de quebra, joga o Cliente criado contendo id gerado novo
    }

    @PutMapping("/{id}") // Acesso quando pedirmos para EDITAR completamente baseado em sua URL e chave 
    public ResponseEntity<Cliente> atualizar(@PathVariable Long id, @RequestBody Cliente clienteAtualizado) {
        if (!clienteRepository.existsById(id)) { // existsById faz query barata verificando pela PK . Caso id nao exista:
            return ResponseEntity.notFound().build(); // Corta fora o metodo e grita 404 pros editores (Postman)
        }
        clienteAtualizado.setId(id); // Se certificar que injetaremos na atualização o Exato ID passado e achado anteriormente e nao um que o user inventou torto
        Cliente salvo = clienteRepository.save(clienteAtualizado); // Esse save percebe ID cheio de update no banco, diferente do caso post empty em que vai no Insert
        return ResponseEntity.ok(salvo); // Tudo rolou show e repassando cliente full 
    }

    @DeleteMapping("/{id}") // Requisições verbo HTML para destruit a chave da URl
    public ResponseEntity<Void> excluir(@PathVariable Long id) { // Mesma logica com ID tirando de endereço web url
        if (!clienteRepository.existsById(id)) { // Precisa existir pra conseguirmos quebrar
            return ResponseEntity.notFound().build(); // Corta de novo status 404
        }
        clienteRepository.deleteById(id); // Manda deletar via ID na query pra economizar
        return ResponseEntity.noContent().build(); // Delete que deu boa retorna NoContent(código 204), pois depois de apagar não há dados sobrando
    }
}

