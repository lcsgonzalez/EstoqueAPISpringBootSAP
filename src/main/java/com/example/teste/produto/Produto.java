package com.example.teste.produto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Table(name="produtos")
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

    @NotNull
    private String nome;
    private String descricao;
    private BigDecimal preco;

    @Size(min=3, max=255)
    private Integer quantidadeEstoque;

}