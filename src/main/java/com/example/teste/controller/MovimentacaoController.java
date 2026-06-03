package com.example.teste.controller;

import com.example.teste.model.Movimentacao;
import com.example.teste.dto.MovimentacaoDTO;
import com.example.teste.service.MovimentacaoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimentacoes")
@CrossOrigin(origins = "*")
public class MovimentacaoController {

    private final MovimentacaoService movimentacaoService;

    public MovimentacaoController(MovimentacaoService movimentacaoService) {
        this.movimentacaoService = movimentacaoService;
    }

    @PostMapping
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Movimentacao> movimentar(@RequestBody MovimentacaoDTO dto) {
        Movimentacao movimentacao = movimentacaoService.movimentar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(movimentacao);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Page<Movimentacao>> listar(@PageableDefault(size = 10) Pageable pageable) {
        Page<Movimentacao> movimentacoes = movimentacaoService.listar(pageable);
        return ResponseEntity.ok(movimentacoes);
    }

    @GetMapping("/todos")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<Movimentacao>> listarTodos() {
        List<Movimentacao> movimentacoes = movimentacaoService.listarTodos();
        return ResponseEntity.ok(movimentacoes);
    }

    @GetMapping("/produto/{produtoId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Page<Movimentacao>> listarPorProduto(@PathVariable Long produtoId, 
                                                              @PageableDefault(size = 10) Pageable pageable) {
        Page<Movimentacao> movimentacoes = movimentacaoService.listarPorProduto(produtoId, pageable);
        return ResponseEntity.ok(movimentacoes);
    }

    @GetMapping("/produto/{produtoId}/todos")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<Movimentacao>> listarPorProdutoTodos(@PathVariable Long produtoId) {
        List<Movimentacao> movimentacoes = movimentacaoService.listarPorProdutoTodos(produtoId);
        return ResponseEntity.ok(movimentacoes);
    }
}