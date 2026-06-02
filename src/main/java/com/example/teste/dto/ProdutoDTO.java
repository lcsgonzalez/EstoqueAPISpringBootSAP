package com.example.teste.dto;

public class ProdutoDTO {

    private String nome;
    private Integer quantidade;
    private Integer limiteMinimo;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Integer getLimiteMinimo() {
        return limiteMinimo;
    }

    public void setLimiteMinimo(Integer limiteMinimo) {
        this.limiteMinimo = limiteMinimo;
    }
}