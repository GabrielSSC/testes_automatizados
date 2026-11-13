package br.edu.ada.catalogo.produtos.domain;

import java.math.BigDecimal;

public record Produto(Long id, String nome, BigDecimal preco, int quantidade) {
}
