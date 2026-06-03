package com.example.teste.movimentacao;

import com.example.teste.produto.Produto;
import jakarta.persistence.*;
import lombok.*;

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

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

}