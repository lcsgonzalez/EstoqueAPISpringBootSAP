package com.example.teste.usuario;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DadosAtualizacaoUsuario(
        @NotNull Long id,
        @Size(min = 3, max = 50) String login,
        Role role
) {}

