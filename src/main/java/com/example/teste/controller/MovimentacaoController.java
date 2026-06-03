package com.example.teste.controller;

import com.example.teste.movimentacao.Movimentacao;
import com.example.teste.dto.MovimentacaoDTO;
import com.example.teste.service.MovimentacaoService;
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
    public Movimentacao movimentar(@RequestBody MovimentacaoDTO dto) {
        return movimentacaoService.movimentar(dto);
    }

    @GetMapping
    public List<Movimentacao> listar() {
        return movimentacaoService.listar();
    }

    @GetMapping("/produto/{produtoId}")
    public List<Movimentacao> listarPorProduto(@PathVariable Long produtoId) {
        return movimentacaoService.listarPorProduto(produtoId);
    }
}