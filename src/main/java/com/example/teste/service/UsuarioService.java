package com.example.teste.service;

import com.example.teste.repository.UsuarioRepository;
import com.example.teste.usuario.DadosAtualizacaoUsuario;
import com.example.teste.usuario.DadosCadastroUsuario;
import com.example.teste.usuario.DadosListagemUsuario;
import com.example.teste.usuario.Usuario;
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
    private UsuarioRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario criarUsuario(DadosCadastroUsuario dados) {
        if (userRepository.existsByLoginAndAtivoTrue(dados.login())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Login já existe");
        }
        Usuario usuario = new Usuario();
        usuario.setLogin(dados.login());
        usuario.setSenha(passwordEncoder.encode(dados.senha()));
        usuario.setRole(dados.role());
        usuario.setAtivo(true);
        return userRepository.save(usuario);

    }
    public Page<DadosListagemUsuario> listarUsuarios(Pageable paginacao) {
        return userRepository.findAllByAtivoTrue(paginacao).map(DadosListagemUsuario::new);
    }
    public Usuario buscarPorId(Long id) {
        return userRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
    }

    public Usuario atualizarUsuario(DadosAtualizacaoUsuario dados) {
        Usuario usuario = buscarPorId(dados.id());
        if (dados.login() != null && !dados.login().isBlank()) {
            // verifica se novo login já existe em outro usuário ativo
            if (userRepository.existsByLoginAndAtivoTrue(dados.login())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Login já está em uso");
            }
            usuario.setLogin(dados.login());
        }
        if (dados.role() != null) {
            usuario.setRole(dados.role());
        }
        return userRepository.save(usuario);
    }
    public void atualizarSenha(Long id, String novaSenha) {
        Usuario usuario = buscarPorId(id);
        usuario.setSenha(passwordEncoder.encode(novaSenha));
        userRepository.save(usuario);
    }

    public void desativarUsuario(Long id) {
        Usuario usuario = buscarPorId(id);
        usuario.setAtivo(false);
        userRepository.save(usuario);
    }
}
