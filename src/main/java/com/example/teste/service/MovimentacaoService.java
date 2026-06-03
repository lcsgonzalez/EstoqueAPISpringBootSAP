package com.example.teste.service;

import com.example.teste.dto.MovimentacaoDTO;
import com.example.teste.model.Movimentacao;
import com.example.teste.model.Produto;
import com.example.teste.repository.MovimentacaoRepository;
import com.example.teste.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    /**
     * Obtém o login do usuário autenticado
     */
    private String obterLoginUsuarioAutenticado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            return auth.getName();
        }
        return "SISTEMA";
    }

    /**
     * Realiza movimentação com validação de estoque
     */
    @Transactional
    public Movimentacao movimentar(MovimentacaoDTO dto) {
        Produto produto = produtoRepository.findById(dto.produtoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        String tipoNormalizado = dto.tipo().toUpperCase();

        if (tipoNormalizado.equalsIgnoreCase("ENTRADA")) {
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + dto.quantidade());
        } else if (tipoNormalizado.equalsIgnoreCase("SAIDA")) {

            if (produto.getQuantidadeEstoque() < dto.quantidade()) {
                throw new RuntimeException("Estoque insuficiente. Disponível: " + 
                    produto.getQuantidadeEstoque() + ", Solicitado: " + dto.quantidade());
            }
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - dto.quantidade());
        } else {
            throw new RuntimeException("Tipo de movimentação inválido. Use ENTRADA ou SAIDA");
        }

        produtoRepository.save(produto);

        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setProduto(produto);
        movimentacao.setTipo(tipoNormalizado);
        movimentacao.setQuantidade(dto.quantidade());
        movimentacao.setDataMovimentacao(LocalDateTime.now());
        movimentacao.setUsuarioLogin(obterLoginUsuarioAutenticado());

        return movimentacaoRepository.save(movimentacao);
    }

    public Page<Movimentacao> listar(Pageable pageable) {
        return movimentacaoRepository.findAll(pageable);
    }

    public List<Movimentacao> listarTodos() {
        return movimentacaoRepository.findAll();
    }

    public Page<Movimentacao> listarPorProduto(Long produtoId, Pageable pageable) {
        return movimentacaoRepository.findByProdutoId(produtoId, pageable);
    }

    public List<Movimentacao> listarPorProdutoTodos(Long produtoId) {
        return movimentacaoRepository.findByProdutoIdOrderByDataMovimentacaoDesc(produtoId);
    }
}