package com.example.teste.produto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query("""
        SELECT DISTINCT p
        FROM Produto p
        LEFT JOIN Movimentacao m ON m.produto.id = p.id
        WHERE (:nome IS NULL OR LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%')))
        AND (
            :inicio IS NULL
            OR :fim IS NULL
            OR m.atualizadoEm BETWEEN :inicio AND :fim
        )
    """)
    Page<Produto> buscarComFiltros(
            @Param("nome") String nome,
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim,
            Pageable pageable
    );
}