package com.example.teste.produto;

import com.example.teste.movimentacao.Movimentacao;
import com.example.teste.movimentacao.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
        this.movimentacaoRepository = movimentacaoRepository;
    }

    public Produto cadastrar(ProdutoDTO dto) {
        Produto produto = new Produto();

        produto.setNome(dto.nome());
        produto.setDescricao(dto.descricao());
        produto.setPreco(dto.preco());
        produto.setQuantidadeEstoque(dto.quantidadeEstoque());

        produto = produtoRepository.save(produto);

        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setTipo("Entrada");
        movimentacao.setQuantidade(dto.quantidadeEstoque());
        movimentacao.setProduto(produto);
        movimentacao.setDataMovimentacao(LocalDateTime.now());

        movimentacaoRepository.save(movimentacao);

        return produto;
    }

    public List<Produto> listar() {
        return produtoRepository.findAll();
    }

    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    public Produto atualizar(Long id, ProdutoDTO dto) {
        Produto produto = buscarPorId(id);

        if(dto.nome()!=null && !dto.nome().isBlank())
            produto.setNome(dto.nome());
        if(dto.descricao()!=null && !dto.descricao().isBlank())
            produto.setDescricao(dto.descricao());
        if(dto.preco()!=null)
            produto.setPreco(dto.preco());
        if(dto.quantidadeEstoque()!=null && dto.quantidadeEstoque()>=0)
            produto.setQuantidadeEstoque(dto.quantidadeEstoque());

        return produtoRepository.save(produto);
    }

    public void deletar(Long id) {
        Produto produto = buscarPorId(id);
        produtoRepository.delete(produto);
    }
}