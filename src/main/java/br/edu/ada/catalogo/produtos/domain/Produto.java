package br.edu.ada.catalogo.produtos.domain;

import java.math.BigDecimal;

public record Produto(Long id, String nome, BigDecimal preco, int quantidade) {
    public Produto {
        if (preco != null && preco.signum() < 0) {
            throw new IllegalArgumentException("O preço não pode ser negativo");
        }
    }
}
