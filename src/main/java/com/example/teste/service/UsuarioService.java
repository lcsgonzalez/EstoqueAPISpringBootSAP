package com.example.teste.service;

import com.example.teste.usuario.DadosCadastro;
import com.example.teste.usuario.Usuario;
import com.example.teste.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public Usuario CriarUsuario(DadosCadastro dados){
        if(repository.existsByLoginAndAtivoTrue(dados.login())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Erro , usuário já cadastrado no sistema!");
        }

        Usuario usuario = new Usuario();
        usuario.setLogin(dados.login());
        usuario.setSenha(dados.senha());
        usuario.setRole(dados.role());
        usuario.setAtivo(true);

        return repository.save(usuario);
    }
}
