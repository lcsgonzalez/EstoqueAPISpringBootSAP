package com.example.teste.controller;

import com.example.teste.service.UsuarioService;
import com.example.teste.usuario.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DadosDetalhamentoUsuario> criarUsuario(@RequestBody @Valid DadosCadastroUsuario dados) {
        var usuario = service.criarUsuario(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(new DadosDetalhamentoUsuario(usuario));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<DadosListagemUsuario>> listarUsuarios(@PageableDefault(size = 10) Pageable paginacao) {
        var page = service.listarUsuarios(paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DadosDetalhamentoUsuario> buscarUsuario(@PathVariable Long id) {
        var usuario = service.buscarPorId(id);
        return ResponseEntity.ok(new DadosDetalhamentoUsuario(usuario));
    }

    @PutMapping
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DadosDetalhamentoUsuario> atualizarUsuario(@RequestBody @Valid DadosAtualizacaoUsuario dados) {
        var usuario = service.atualizarUsuario(dados);
        return ResponseEntity.ok(new DadosDetalhamentoUsuario(usuario));
    }

    @PutMapping("/{id}/senha")
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> redefinirSenha(@PathVariable Long id, @RequestBody @Valid RedefinirSenhaDTO dto) {
        service.atualizarSenha(id, dto.novaSenha());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        service.desativarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
