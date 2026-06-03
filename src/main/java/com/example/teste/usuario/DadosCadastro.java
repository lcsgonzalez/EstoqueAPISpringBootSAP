package com.example.teste.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DadosCadastro(
        @NotBlank
        @Size(min = 3, max = 50)
        String login,
        @NotBlank @Size(min = 6, max = 100)
        String senha,
        @NotNull Role role
) {
}
