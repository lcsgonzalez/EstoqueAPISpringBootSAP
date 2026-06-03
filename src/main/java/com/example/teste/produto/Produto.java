package com.example.teste.produto;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Table(name="produto")
@Entity(name="Produto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Integer quantidadeEstoque;

}