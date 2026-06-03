package com.example.teste.service;

import com.example.teste.movimentacao.Movimentacao;
import com.example.teste.movimentacao.MovimentacaoDTO;
import com.example.teste.movimentacao.MovimentacaoRepository;
import com.example.teste.produto.Produto;
import com.example.teste.produto.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovimentacaoService {

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public MovimentacaoService(
            MovimentacaoRepository movimentacaoRepository,
            ProdutoRepository produtoRepository
    ) {
        this.movimentacaoRepository = movimentacaoRepository;
        this.produtoRepository = produtoRepository;
    }

    public Movimentacao movimentar(MovimentacaoDTO dto) {
        Produto produto = produtoRepository.findById(dto.produtoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (dto.tipo().equalsIgnoreCase("ENTRADA")) {
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + dto.quantidade());
        } else {
            throw new RuntimeException("Tipo de movimentação inválido. Use ENTRADA ou SAIDA");
        }

        produtoRepository.save(produto);

        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setProduto(produto);
        movimentacao.setTipo(dto.tipo().toUpperCase());
        movimentacao.setQuantidade(dto.quantidade());

        return movimentacaoRepository.save(movimentacao);
    }

    public List<Movimentacao> listar() {
        return movimentacaoRepository.findAll();
    }

    public List<Movimentacao> listarPorProduto(Long produtoId) {
        return movimentacaoRepository.findByProdutoId(produtoId);
    }
}