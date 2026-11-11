package br.edu.ada.catalogo.produtos.boundary;

import br.edu.ada.catalogo.produtos.domain.Produto;
import java.math.BigDecimal;

public record ProdutoResponse(Long id, String nome, BigDecimal preco, int quantidade) {
    public static ProdutoResponse from(Produto p) {
        return new ProdutoResponse(p.id(), p.nome(), p.preco(), p.quantidade());
    }
}
