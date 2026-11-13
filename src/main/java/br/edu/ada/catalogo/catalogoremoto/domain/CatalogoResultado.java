package br.edu.ada.catalogo.catalogoremoto.domain;

import java.util.List;

public record CatalogoResultado(List<ProdutoDisponivel> products, String origem) {
    public record ProdutoDisponivel(Long id, String name, Integer stock) {
    }
}
