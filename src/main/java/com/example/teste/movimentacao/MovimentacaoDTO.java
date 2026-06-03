package com.example.teste.movimentacao;

public record MovimentacaoDTO(
    Long produtoId,
    String tipo,
    Integer quantidade
){}