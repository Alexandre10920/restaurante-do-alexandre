package br.com.senai.deliveryavaliacao.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Optional;

import br.com.senai.deliveryavaliacao.model.Produto;
import br.com.senai.deliveryavaliacao.service.ProdutoService;
import br.com.senai.deliveryavaliacao.dto.ProdutoRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    
    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<Produto> cadastrar(
            @Valid @RequestBody ProdutoRequest dadosProduto) {

        Integer restauranteId = dadosProduto.getRestauranteId();

            if (restauranteId == null) {
                return ResponseEntity.badRequest().build();
            }

        Optional<Produto> produto = produtoService.cadastrar(dadosProduto);

        if (produto.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(produto.get());
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listar() {

        List<Produto> produtos = produtoService.listar();
        
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(
            @PathVariable Integer id) {

        Optional<Produto> produtoEncontrado = produtoService.buscarPorId(id);

        if (produtoEncontrado.isPresent()) {
            return ResponseEntity.ok(produtoEncontrado.get());
        }

        return ResponseEntity.notFound().build();
        
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(
        @PathVariable Integer id,
        @Valid @RequestBody ProdutoRequest dados) {

        Optional<Produto> produtoAtualizado = produtoService.atualizar(id, dados);

        if (produtoAtualizado.isPresent()) {
            return ResponseEntity.ok(produtoAtualizado.get());
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
        @PathVariable Integer id) {

        boolean excluido = produtoService.excluir(id);

        if (excluido) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
