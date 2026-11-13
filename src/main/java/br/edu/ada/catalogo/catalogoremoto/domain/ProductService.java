package br.edu.ada.catalogo.catalogoremoto.domain;

import br.edu.ada.catalogo.catalogoremoto.integration.estoque.EstoqueGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final EstoqueGateway gateway;
    private final RetryTemplate retry = RetryTemplate.builder().maxAttempts(3).fixedBackoff(200).build();

    public CatalogoResultado getProducts() {
        return retry.execute(ctx -> {
            var externo = gateway.consultar();
            if (externo == null || externo.products() == null) {
                throw new IllegalStateException("Estoque retornou payload inválido");
            }
            var disponiveis = externo.products().stream().filter(p -> p.stock() > 0)
                    .map(p -> new CatalogoResultado.ProdutoDisponivel((long) p.id(), p.title(), p.stock())).toList();
            return new CatalogoResultado(disponiveis, "ESTOQUE");
        }, ctx -> new CatalogoResultado(List.of(), "FALLBACK"));
    }
}
