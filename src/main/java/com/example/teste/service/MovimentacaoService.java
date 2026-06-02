package com.example.teste.service;

import com.example.teste.dto.MovimentacaoDTO;
import com.example.teste.model.Movimentacao;
import com.example.teste.model.Produto;
import com.example.teste.repository.MovimentacaoRepository;
import com.example.teste.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MovimentacaoService {

    private final MovimentacaoRepository movimentacaoRepository;
    private final ProdutoRepository produtoRepository;

    public MovimentacaoService(
            MovimentacaoRepository movimentacaoRepository,
            ProdutoRepository produtoRepository
    ) {
        this.movimentacaoRepository = movimentacaoRepository;
        this.produtoRepository = produtoRepository;
    }

    public Movimentacao movimentar(MovimentacaoDTO dto) {
        Produto produto = produtoRepository.findById(dto.getProdutoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (dto.getTipo().equalsIgnoreCase("ENTRADA")) {
            produto.setQuantidade(produto.getQuantidade() + dto.getQuantidade());
        } else if (dto.getTipo().equalsIgnoreCase("SAIDA")) {

            if (produto.getQuantidade() < dto.getQuantidade()) {
                throw new RuntimeException("Estoque insuficiente");
            }

            produto.setQuantidade(produto.getQuantidade() - dto.getQuantidade());
        } else {
            throw new RuntimeException("Tipo de movimentação inválido. Use ENTRADA ou SAIDA");
        }

        produtoRepository.save(produto);

        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setProduto(produto);
        movimentacao.setTipo(dto.getTipo().toUpperCase());
        movimentacao.setQuantidade(dto.getQuantidade());
        movimentacao.setDataMovimentacao(LocalDateTime.now());

        return movimentacaoRepository.save(movimentacao);
    }

    public List<Movimentacao> listar() {
        return movimentacaoRepository.findAll();
    }

    public List<Movimentacao> listarPorProduto(Long produtoId) {
        return movimentacaoRepository.findByProdutoId(produtoId);
    }
}