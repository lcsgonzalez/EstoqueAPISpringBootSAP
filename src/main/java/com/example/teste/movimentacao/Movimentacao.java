package com.example.teste.movimentacao;

import com.example.teste.produto.Produto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "movimentacao")
@Entity(name = "Movimentacao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;

    private Integer quantidade;

    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;
}