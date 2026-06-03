package com.example.teste.dto;

import com.example.teste.model.Usuario;
import com.example.teste.usuario.Role;

public record DadosListagemUsuario(Long id, String login, Role role, boolean ativo) {
    public DadosListagemUsuario(Usuario usuario) {
        this(usuario.getId(), usuario.getLogin(), usuario.getRole(), usuario.isAtivo());
    }
}