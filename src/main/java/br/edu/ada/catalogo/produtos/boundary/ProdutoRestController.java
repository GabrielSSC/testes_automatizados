package br.edu.ada.catalogo.produtos.boundary;

import br.edu.ada.catalogo.produtos.domain.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
public class ProdutoRestController {
    private final ProdutoService service;

    @GetMapping
    public List<ProdutoResponse> listar(@RequestParam(required = false) String nome) {
        return service.listar(nome).stream().map(ProdutoResponse::from).toList();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ProdutoResponse buscar(@PathVariable Long id) {
        return ProdutoResponse.from(service.buscar(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProdutoResponse> criar(@Valid @RequestBody ProdutoRequest request) {
        ProdutoResponse produto = ProdutoResponse
                .from(service.criar(request.nome(), request.preco(), request.quantidade()));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(produto.id())
                .toUri();
        return ResponseEntity.created(location).body(produto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ProdutoResponse atualizar(@PathVariable Long id, @Valid @RequestBody ProdutoRequest request) {
        return ProdutoResponse.from(service.atualizar(id, request.nome(), request.preco(), request.quantidade()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/retiradas")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ProdutoResponse retirar(@PathVariable Long id, @Valid @RequestBody RetiradaRequest request) {
        return ProdutoResponse.from(service.retirarEstoque(id, request.quantidade()));
    }
}
