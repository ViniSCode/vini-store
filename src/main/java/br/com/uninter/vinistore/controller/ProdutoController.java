package br.com.uninter.vinistore.controller; // MVC Controller responsável pelas funções voltadas as tabelas ligadas por Repository (Produto)

import br.com.uninter.vinistore.model.Produto; // O model/entidade do nosso BD
import br.com.uninter.vinistore.repository.ProdutoRepository; // A ponte do nosso Java que age na tabela Model via comandos JPA prontos
import org.springframework.beans.factory.annotation.Autowired; // Anotação responsável por carregar instancias autómaticas (Injeção de dependencias) do Spring
import org.springframework.http.HttpStatus; // Trabalha devolvendo o código para front-end e softwares de Rest API tipo Postman indicando 200 pro Sucesso, ou 404 erro, e tal    
import org.springframework.http.ResponseEntity; // Classe que permite retornar objetos mais complexos e códigos definidos numa resposta inteira pro Postman   
import org.springframework.web.bind.annotation.*; // Agrupa em uma única * as ferramentas tipo os Mapping de Get Post ou etc

import java.util.List; // Uma lista é necessária pra retornar muitos produtos em JSON
import java.util.Optional; // Um tipo de container pra prevenir "NullPointerException" em Buscas Vazias por um Produto 

@RestController // Diz que nossa classe inteira fará papel REST de Controlador JSON nas APIs
@RequestMapping("/produtos") // O recurso URL geral para os navegadores e postmans /produtos
public class ProdutoController {

    @Autowired // O Autowired pede pro Spring cuidar, fabricar e entregar na classe atual o objeto "produtoRepository" vivo sem se preocuparmos com New
    private ProdutoRepository produtoRepository;

    @GetMapping // O mapeamento padrão do endereço acima usando requisições GET
    public List<Produto> listarTodos() { 
        return produtoRepository.findAll();  // Repositorio executa uma leitura inteira (Select *) e retorna em cascata uma List<> cheia pro Postman
    }

    @GetMapping("/{id}") // Se acessar GET em /produtos/50 esse mapeamento se engatilha e passa "50" pelo caminho
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) { // Anotação Pega este /50 da url de destino pra esse parametro
        Optional<Produto> produto = produtoRepository.findById(id); // O banco tenta caçar se a chave primaria existe via repositório. Usa container Optional evitando quebrar caso encontre lixo.
        return produto.map(ResponseEntity::ok) // Caso encontre de jeito limpo o produto é mapeado no corpo com OK 200 HTTP code
                .orElseGet(() -> ResponseEntity.notFound().build()); // Tratamento com expressao lambda para dizer 404 Not Found caso falhe e não há corpo body.
    }

    @PostMapping // Quando vier pela requisição via POST do postman (Requisições pra Escrever / Inserir novo)
    @ResponseStatus(HttpStatus.CREATED) // Para uma resposta de Sucesso exata "Status: 201 Created" em verde no postman
    public Produto cadastrar(@RequestBody Produto produto) { // E o body do json {"nome": "baozi"} transforma magico pra instancia var JAVA no ato da chegada aqui
        return produtoRepository.save(produto); // Retorna a execucaco do Save do Repo e também vai com id novinho junto   
    }

    @PutMapping("/{id}") // Usado para substituir totalmente atualizando e recebendo parametros em body e endereço por ID URL
    public ResponseEntity<Produto> atualizar(@PathVariable Long id, @RequestBody Produto produtoAtualizado) {
        if (!produtoRepository.existsById(id)) { // Faz contagem verificando presença base de dados
            return ResponseEntity.notFound().build(); // Falhou se nem tá lá , mande 404
        }
        produtoAtualizado.setId(id); // Cuidamos de prender forte que o objeto de produtoAtualizado body use unicamente a Key que a rota pediu
        Produto salvo = produtoRepository.save(produtoAtualizado); // E por se ter chave na base e objeto cheios, o save no Repository do spring atua como "Update" invés de Criar Do zero.
        return ResponseEntity.ok(salvo); // Devolve o OK (200 status) no ato mostrando ele lindo e editado na tela de resposta do requistor
    }

    @DeleteMapping("/{id}") // Delete mapping só com a Chave no path. Requisição sem corpo algum. 
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!produtoRepository.existsById(id)) { // Precisa existir pra sumir da prateleira antes de explodir java. Verifica no bd rapidinho
            return ResponseEntity.notFound().build(); // Ou lança seu 404 e morre de levis.
        }
        produtoRepository.deleteById(id); // Exclusao direta sem medo pelo method já nativo
        return ResponseEntity.noContent().build(); // E na boa manda que rolou sem conter mais "nada": é pra isso o Status HTTP 204 No Content!!
    }
}

