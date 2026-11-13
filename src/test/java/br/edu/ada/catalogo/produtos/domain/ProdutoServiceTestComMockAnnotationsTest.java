package br.edu.ada.catalogo.produtos.domain;

import br.edu.ada.catalogo.produtos.repository.ProdutoEntity;
import br.edu.ada.catalogo.produtos.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTestComMockAnnotationsTest {
    @Mock
    ProdutoRepository repository;
    @InjectMocks
    ProdutoService service;

    @Test
    void givenProdutoComEstoque_whenRetirarQuantidadeValida_thenReduzirEstoque() {
        // Given
        var entity = new ProdutoEntity("Mouse", new BigDecimal("85.00"), 10);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        // When
        var produto = service.retirarEstoque(1L, 3);
        // Then
        assertEquals(7, produto.quantidade());
        assertEquals(7, entity.getQuantidade());
        verify(repository).findById(1L);
    }

}
