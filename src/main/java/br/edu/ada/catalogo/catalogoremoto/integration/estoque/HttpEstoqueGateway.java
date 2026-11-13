package br.edu.ada.catalogo.catalogoremoto.integration.estoque;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class HttpEstoqueGateway implements EstoqueGateway {
    private final RestClient restClient;

    public HttpEstoqueGateway(RestClient.Builder builder, @Value("${catalogo.estoque.url}") String url) {
        this.restClient = builder.baseUrl(url).build();
    }

    @Override
    public CatalogoExterno consultar() {
        return restClient.get().uri("/products").retrieve().body(CatalogoExterno.class);
    }
}
