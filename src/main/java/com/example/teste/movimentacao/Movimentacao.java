package com.example.teste.movimentacao;

import com.example.teste.produto.Produto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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

    @Size(min=3, max=255)
    private Integer quantidade;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

}