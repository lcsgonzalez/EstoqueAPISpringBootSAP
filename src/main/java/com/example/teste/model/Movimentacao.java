package com.example.teste.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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
    private LocalDateTime dataMovimentacao;
    private String usuarioLogin;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

}