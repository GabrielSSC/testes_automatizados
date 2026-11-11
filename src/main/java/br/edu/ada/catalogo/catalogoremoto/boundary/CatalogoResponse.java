package br.edu.ada.catalogo.catalogoremoto.boundary;

import br.edu.ada.catalogo.catalogoremoto.domain.CatalogoResultado;
import java.util.List;

public record CatalogoResponse(List<ProdutoDto> products, int total, String origem) {
    public record ProdutoDto(Long id, String name, Integer stock) {
    }

    public static CatalogoResponse from(CatalogoResultado resultado) {
        var produtos = resultado.products().stream().map(p -> new ProdutoDto(p.id(), p.name(), p.stock())).toList();
        return new CatalogoResponse(produtos, produtos.size(), resultado.origem());
    }
}
