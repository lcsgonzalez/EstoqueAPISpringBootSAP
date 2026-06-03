package com.example.teste.dto;

import com.example.teste.model.Usuario;
import com.example.teste.usuario.Role;

public record DadosDetalhamentoUsuario(Long id, String login, Role role, boolean ativo) {
    public DadosDetalhamentoUsuario(Usuario usuario) {
        this(usuario.getId(), usuario.getLogin(), usuario.getRole(), usuario.isAtivo());
    }
}