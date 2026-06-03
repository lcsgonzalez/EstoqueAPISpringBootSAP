package com.example.teste.usuarios;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RedefinirSenhaDTO(@NotBlank @Size(min = 6) String novaSenha) {}
