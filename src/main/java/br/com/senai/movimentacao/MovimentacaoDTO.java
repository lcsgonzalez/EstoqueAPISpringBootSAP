package br.com.senai.movimentacao;

public record MovimentacaoDTO(
    Long produtoId,
    String tipo,
    Integer quantidade

){}