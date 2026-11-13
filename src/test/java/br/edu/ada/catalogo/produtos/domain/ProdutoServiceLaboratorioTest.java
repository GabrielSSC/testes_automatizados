package br.edu.ada.catalogo.produtos.domain;

import br.edu.ada.catalogo.produtos.repository.ProdutoRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceLaboratorioTest {
    @Mock ProdutoRepository repository;
    @InjectMocks ProdutoService service;

    @Test
    @Disabled("LABORATORIO: habilite o teste após implementar")
    void givenProdutoInexistente_whenBuscar_thenLancarNaoEncontrado() {
        // Given: simule repository.findById retornando Optional.empty().
        // When: invoque service.buscar(99L).
        // Then: assertThrows(ProdutoNaoEncontradoException.class, ...).
        fail("TODO: completar teste");
    }

    @Test
    @Disabled("LABORATORIO: habilite o teste após implementar")
    void givenEstoqueInsuficiente_whenRetirar_thenNaoAlterarQuantidade() {
        // Given: simule uma entidade com estoque menor que a retirada.
        // When/Then: assertThrows e verifique que quantidade não mudou.
        fail("TODO: completar teste");
    }
}
