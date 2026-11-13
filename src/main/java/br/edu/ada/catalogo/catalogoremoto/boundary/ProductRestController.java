package br.edu.ada.catalogo.catalogoremoto.boundary;

import br.edu.ada.catalogo.catalogoremoto.domain.ProductService;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductRestController {
    private final ProductService service;
    private final MeterRegistry meterRegistry;

    @GetMapping("/products")
    public ResponseEntity<CatalogoResponse> listar() {
        var resposta = CatalogoResponse.from(service.getProducts());
        meterRegistry.counter("catalogo.consultas", "origem", resposta.origem()).increment();
        return "FALLBACK".equals(resposta.origem()) ? ResponseEntity.status(503).body(resposta)
                : ResponseEntity.ok(resposta);
    }
}
