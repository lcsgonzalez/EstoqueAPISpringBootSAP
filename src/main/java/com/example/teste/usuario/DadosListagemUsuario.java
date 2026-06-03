package com.example.teste.usuario;

public record DadosListagemUsuario(Long id, String login, Role role, boolean ativo) {
    public DadosListagemUsuario(Usuario usuario) {
        this(usuario.getId(), usuario.getLogin(), usuario.getRole(), usuario.isAtivo());
    }
}
