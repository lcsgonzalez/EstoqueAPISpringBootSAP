package com.example.teste.service;

import com.example.teste.dto.ProdutoDTO;
import com.example.teste.model.Produto;
import com.example.teste.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto cadastrar(ProdutoDTO dto) {
        Produto produto = new Produto();

        produto.setNome(dto.getNome());
        produto.setQuantidade(dto.getQuantidade());
        produto.setLimiteMinimo(dto.getLimiteMinimo());

        return produtoRepository.save(produto);
    }

    public List<Produto> listar() {
        return produtoRepository.findAll();
    }

    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    public Produto atualizar(Long id, ProdutoDTO dto) {
        Produto produto = buscarPorId(id);

        produto.setNome(dto.getNome());
        produto.setQuantidade(dto.getQuantidade());
        produto.setLimiteMinimo(dto.getLimiteMinimo());

        return produtoRepository.save(produto);
    }

    public void deletar(Long id) {
        Produto produto = buscarPorId(id);
        produtoRepository.delete(produto);
    }

    public List<Produto> listarAbaixoDoLimite() {
        return produtoRepository.findAll()
                .stream()
                .filter(produto -> produto.getQuantidade() <= produto.getLimiteMinimo())
                .toList();
    }
}