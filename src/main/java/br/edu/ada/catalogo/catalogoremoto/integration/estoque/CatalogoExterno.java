package br.edu.ada.catalogo.catalogoremoto.integration.estoque;

import java.util.List;

public record CatalogoExterno(List<ProdutoExterno> products, int total) {
}
