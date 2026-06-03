package com.example.teste.service;

import com.example.teste.movimentacao.Movimentacao;
import com.example.teste.movimentacao.MovimentacaoDTO;
import com.example.teste.movimentacao.MovimentacaoRepository;
import com.example.teste.produto.Produto;
import com.example.teste.produto.ProdutoRepository;
import com.example.teste.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovimentacaoService {

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public MovimentacaoService(
            MovimentacaoRepository movimentacaoRepository,
            ProdutoRepository produtoRepository
    ) {
        this.movimentacaoRepository = movimentacaoRepository;
        this.produtoRepository = produtoRepository;
    }

    public Movimentacao movimentar(MovimentacaoDTO dto) {

        Produto produto = produtoRepository.findById(dto.produtoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        Usuario usuario = (Usuario) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (dto.quantidade() == null || dto.quantidade() <= 0) {
            throw new RuntimeException("A quantidade deve ser maior que zero");
        }

        String tipo = dto.tipo().toUpperCase();

        switch (tipo) {

            case "ENTRADA":
                produto.setQuantidadeEstoque(
                        produto.getQuantidadeEstoque() + dto.quantidade()
                );
                break;

            case "SAIDA":

                Integer estoqueAtual = produto.getQuantidadeEstoque();

                if (dto.quantidade() > estoqueAtual) {
                    throw new RuntimeException(
                            "Saída não permitida: estoque insuficiente. Disponível: "
                                    + estoqueAtual
                                    + ". Solicitado: "
                                    + dto.quantidade()
                    );
                }

                produto.setQuantidadeEstoque(
                        estoqueAtual - dto.quantidade()
                );

                break;

            default:
                throw new RuntimeException(
                        "Tipo de movimentação inválido. Use ENTRADA ou SAIDA"
                );
        }

        produtoRepository.save(produto);

        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setProduto(produto);
        movimentacao.setUsuario(usuario);
        movimentacao.setTipo(tipo);
        movimentacao.setQuantidade(dto.quantidade());

        return movimentacaoRepository.save(movimentacao);
    }

    public List<Movimentacao> listar() {
        return movimentacaoRepository.findAll();
    }

    public List<Movimentacao> listarPorProduto(Long produtoId) {
        return movimentacaoRepository.findByProdutoId(produtoId);
    }
}