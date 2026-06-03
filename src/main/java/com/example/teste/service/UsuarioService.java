package com.example.teste.service;

import com.example.teste.dto.DadosAtualizacaoUsuario;
import com.example.teste.dto.DadosCadastroUsuario;
import com.example.teste.dto.DadosListagemUsuario;
import com.example.teste.model.Usuario;
import com.example.teste.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder; // BCrypt

    public Usuario criarUsuario(DadosCadastroUsuario dados) {
        if (repository.existsByLoginAndAtivoTrue(dados.login())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Login já existe");
        }
        Usuario usuario = new Usuario();
        usuario.setLogin(dados.login());
        usuario.setSenha(passwordEncoder.encode(dados.senha())); // BCrypt + salt automático
        usuario.setRole(dados.role());
        usuario.setAtivo(true);
        return repository.save(usuario);
    }

    public Page<DadosListagemUsuario> listarUsuarios(Pageable paginacao) {
        return repository.findAllByAtivoTrue(paginacao).map(DadosListagemUsuario::new);
    }

    public Usuario buscarPorId(Long id) {
        return repository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
    }

    public Usuario atualizarUsuario(DadosAtualizacaoUsuario dados) {
        Usuario usuario = buscarPorId(dados.id());
        if (dados.login() != null && !dados.login().isBlank()) {
            // verifica se novo login já existe em outro usuário ativo
            if (repository.existsByLoginAndAtivoTrue(dados.login())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Login já está em uso");
            }
            usuario.setLogin(dados.login());
        }
        if (dados.role() != null) {
            usuario.setRole(dados.role());
        }
        return repository.save(usuario);
    }

    public void atualizarSenha(Long id, String novaSenha) {
        Usuario usuario = buscarPorId(id);
        usuario.setSenha(passwordEncoder.encode(novaSenha));
        repository.save(usuario);
    }

    public void desativarUsuario(Long id) {
        Usuario usuario = buscarPorId(id);
        usuario.setAtivo(false);
        repository.save(usuario);
    }
}
