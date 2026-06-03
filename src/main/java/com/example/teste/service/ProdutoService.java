package com.example.teste.service;

import com.example.teste.produto.Produto;
import com.example.teste.produto.ProdutoDTO;
import com.example.teste.produto.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

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

    public Page<Produto> listar(
            String nome,
            LocalDate dataInicio,
            LocalDate dataFim,
            int pagina,
            int tamanho
    ) {

        Pageable pageable = PageRequest.of(
                pagina,
                tamanho,
                Sort.by("id").descending()
        );

        LocalDateTime inicio = null;
        LocalDateTime fim = null;

        if (dataInicio != null) {
            inicio = dataInicio.atStartOfDay();
        }

        if (dataFim != null) {
            fim = dataFim.atTime(23, 59, 59);
        }

        return produtoRepository.buscarComFiltros(
                nome,
                inicio,
                fim,
                pageable
        );
    }

    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    public Produto atualizar(Long id, ProdutoDTO dto) {
        Produto produto = buscarPorId(id);

        if (dto.nome() != null && !dto.nome().isBlank())
            produto.setNome(dto.nome());

        if (dto.descricao() != null && !dto.descricao().isBlank())
            produto.setDescricao(dto.descricao());

        if (dto.preco() != null)
            produto.setPreco(dto.preco());

        if (dto.quantidadeEstoque() != null && dto.quantidadeEstoque() >= 0)
            produto.setQuantidadeEstoque(dto.quantidadeEstoque());

        return produtoRepository.save(produto);
    }

    public void deletar(Long id) {
        Produto produto = buscarPorId(id);
        produtoRepository.delete(produto);
    }
}