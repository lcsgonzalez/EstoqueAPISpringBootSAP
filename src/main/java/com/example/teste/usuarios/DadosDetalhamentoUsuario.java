package com.example.teste.usuarios;

public record DadosDetalhamentoUsuario(Long id, String login, Role role, boolean ativo) {
    public DadosDetalhamentoUsuario(Usuario usuario) {
        this(usuario.getId(), usuario.getLogin(), usuario.getRole(), usuario.isAtivo());
    }
}
