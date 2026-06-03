package com.example.teste.dto;

import java.time.LocalDateTime;

public record MovimentacaoDTO(
    Long produtoId,
    String tipo,
    Integer quantidade,
    LocalDateTime dataMovimentacao,
    String usuario
){}