package com.example.teste.repository;

import com.example.teste.model.Movimentacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {

    List<Movimentacao> findByProdutoId(Long produtoId);

    Page<Movimentacao> findByProdutoId(Long produtoId, Pageable pageable);

    List<Movimentacao> findByProdutoIdOrderByDataMovimentacaoDesc(Long produtoId);
}