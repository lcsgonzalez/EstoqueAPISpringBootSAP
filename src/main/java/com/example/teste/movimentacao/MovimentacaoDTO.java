package com.example.teste.movimentacao;

import com.example.teste.produto.Produto;

public record MovimentacaoDTO(
    Long produtoId,
    String tipo,
    Integer quantidade,
    Produto produto
){}