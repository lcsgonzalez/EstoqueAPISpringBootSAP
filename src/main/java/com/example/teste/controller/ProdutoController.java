package com.example.teste.controller;

import com.example.teste.produto.Produto;
import com.example.teste.produto.ProdutoDTO;
import com.example.teste.service.ProdutoService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public Produto cadastrar(@RequestBody ProdutoDTO dto) {
        return produtoService.cadastrar(dto);
    }

    @GetMapping
    public Page<Produto> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) LocalDate dataInicio,
            @RequestParam(required = false) LocalDate dataFim,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanho
    ) {
        return produtoService.listar(
                nome,
                dataInicio,
                dataFim,
                pagina,
                tamanho
        );
    }

    @GetMapping("/{id}")
    public Produto buscarPorId(@PathVariable Long id) {
        return produtoService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Produto atualizar(@PathVariable Long id, @RequestBody ProdutoDTO dto) {
        return produtoService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public String deletar(@PathVariable Long id) {
        produtoService.deletar(id);
        return "Produto deletado com sucesso";
    }

}