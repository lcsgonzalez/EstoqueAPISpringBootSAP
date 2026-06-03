package com.example.teste.movimentacao;

import com.example.teste.produto.Produto;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Table(name="movimentacao")
@Entity(name="Movimentacao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tipo;
    private Integer quantidade;
    private LocalDateTime data_movimentacao;
    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    public Movimentacao(MovimentacaoDTO dados) {
        this.tipo = dados.tipo();
        this.quantidade = dados.quantidade();
        this.data_movimentacao = LocalDateTime.now();
        this.produto = dados.produto();
    }
}