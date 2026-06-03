package com.example.teste.service;

import com.example.teste.dto.ProdutoDTO;
import com.example.teste.model.Produto;
import com.example.teste.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public Produto cadastrar(ProdutoDTO dto) {
        Produto produto = new Produto();

        produto.setNome(dto.nome());
        produto.setDescricao(dto.descricao());
        produto.setPreco(dto.preco());
        produto.setQuantidadeEstoque(dto.quantidadeEstoque());

        return produtoRepository.save(produto);
    }

    public Page<Produto> listar(Pageable pageable) {
        return produtoRepository.findAll(pageable);
    }

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    @Transactional
    public Produto atualizar(Long id, ProdutoDTO dto) {
        Produto produto = buscarPorId(id);

        if(dto.nome()!=null && !dto.nome().isBlank())
            produto.setNome(dto.nome());
        if(dto.descricao()!=null && !dto.descricao().isBlank())
            produto.setDescricao(dto.descricao());
        if(dto.preco()!=null)
            produto.setPreco(dto.preco());
        if(dto.quantidadeEstoque()!=null && dto.quantidadeEstoque()>=0)
            produto.setQuantidadeEstoque(dto.quantidadeEstoque());

        return produtoRepository.save(produto);
    }

    @Transactional
    public void deletar(Long id) {
        Produto produto = buscarPorId(id);
        produtoRepository.delete(produto);
    }
}