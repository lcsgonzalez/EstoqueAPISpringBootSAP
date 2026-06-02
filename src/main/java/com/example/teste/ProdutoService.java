package com.example.teste;

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

        produto.setNome(dto.nome());
        produto.setDescricao(dto.descricao());
        produto.setPreco(dto.preco());
        produto.setQuantidadeEstoque(dto.quantidadeEstoque());

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

    public void deletar(Long id) {
        Produto produto = buscarPorId(id);
        produtoRepository.delete(produto);
    }
}