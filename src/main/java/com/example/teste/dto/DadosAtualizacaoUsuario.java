package com.example.teste.dto;

import com.example.teste.usuario.Role;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DadosAtualizacaoUsuario(
        @NotNull Long id,
        @Size(min = 3, max = 50) String login,
        Role role
) {}
