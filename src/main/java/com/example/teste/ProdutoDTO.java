package com.example.teste;

import java.math.BigDecimal;

public record ProdutoDTO(
        String nome,
        String descricao,
        BigDecimal preco,
        Integer quantidadeEstoque
) {
}
