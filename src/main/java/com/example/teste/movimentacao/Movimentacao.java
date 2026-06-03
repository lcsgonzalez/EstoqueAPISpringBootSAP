package com.example.teste.movimentacao;

import com.example.teste.produto.Produto;
import com.example.teste.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

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
    private LocalDateTime dataMovimentacao;
    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

}