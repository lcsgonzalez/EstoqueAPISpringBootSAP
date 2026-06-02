package com.example.teste.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;

    private Integer quantidade;

    private LocalDateTime dataMovimentacao;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDateTime getDataMovimentacao() {
        return dataMovimentacao;
    }

    public void setDataMovimentacao(LocalDateTime dataMovimentacao) {
        this.dataMovimentacao = dataMovimentacao;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}