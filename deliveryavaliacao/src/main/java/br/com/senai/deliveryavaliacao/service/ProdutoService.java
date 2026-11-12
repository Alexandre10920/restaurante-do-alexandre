package br.com.senai.deliveryavaliacao.service;

import org.springframework.stereotype.Service;

import br.com.senai.deliveryavaliacao.dto.ProdutoRequest;
import br.com.senai.deliveryavaliacao.model.Produto;
import br.com.senai.deliveryavaliacao.model.Restaurante;
import br.com.senai.deliveryavaliacao.repository.ProdutoRepository;
import br.com.senai.deliveryavaliacao.repository.RestauranteRepository;

import java.util.List;
import java.util.Optional;

@Service 
public class ProdutoService {
    
    private final ProdutoRepository produtoRepository;
    private final RestauranteRepository restauranteRepository;

    public ProdutoService(ProdutoRepository produtoRepository, RestauranteRepository restauranteRepository) {
        this.produtoRepository = produtoRepository;
        this.restauranteRepository = restauranteRepository;
    }

    public Optional<Produto> cadastrar(
        ProdutoRequest dadosProduto) {
        
        Optional<Restaurante> restauranteEncontrado = restauranteRepository.findById(dadosProduto.getRestauranteId());
        
            Produto produto = new Produto();

        if (restauranteEncontrado.isEmpty()) {
            return Optional.empty();
        }

        
        produto.setNome(dadosProduto.getNome());
        produto.setPreco(dadosProduto.getPreco());
        produto.setRestaurante(restauranteEncontrado.get());

        return Optional.of(produtoRepository.save(produto));
    }

    public List<Produto> listar() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> buscarPorId(Integer id) {
        return produtoRepository.findById(id);
    }

    public Optional<Produto> atualizar(
            Integer id, ProdutoRequest novosDados) {

        Optional<Produto> produtoEncontrado = produtoRepository.findById(id);

        if (produtoEncontrado.isEmpty()) {
            return Optional.empty();
        }
        Optional<Restaurante> restauranteEncontrado = restauranteRepository.findById(novosDados.getRestauranteId());

        if (restauranteEncontrado.isEmpty()) {
            return Optional.empty();
        }

        Produto produto = produtoEncontrado.get();

        produto.setNome(novosDados.getNome());
        produto.setPreco(novosDados.getPreco());
        produto.setRestaurante(restauranteEncontrado.get());

        return Optional.of(produtoRepository.save(produto));
    }

    public boolean excluir(Integer id) {

        if (!produtoRepository.existsById(id)) {
            return false;
        }

        produtoRepository.deleteById(id);
        return true;
    }
}
