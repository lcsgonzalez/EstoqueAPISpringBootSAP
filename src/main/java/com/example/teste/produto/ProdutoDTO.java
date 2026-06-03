package com.example.teste.produto;

import java.math.BigDecimal;

public record ProdutoDTO(
        String nome,
        String descricao,
        BigDecimal preco,
        Integer quantidadeEstoque
) {
}
