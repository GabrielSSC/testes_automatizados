package br.edu.ada.catalogo.produtos.domain;

import br.edu.ada.catalogo.produtos.repository.ProdutoEntity;
import br.edu.ada.catalogo.produtos.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {
    @Mock
    ProdutoRepository repository;
    @InjectMocks
    ProdutoService service;

    @Test
    void givenNomeInformado_whenListar_thenRetornarProdutosEncontrados() {
        var entity = new ProdutoEntity("Mouse sem fio", new BigDecimal("85.00"), 8);
        entity.setId(1L);
        when(repository.findByNomeContainingIgnoreCaseOrderByIdAsc("mouse")).thenReturn(List.of(entity));

        var produtos = service.listar("mouse");

        assertEquals(1, produtos.size());
        assertEquals(new Produto(1L, "Mouse sem fio", new BigDecimal("85.00"), 8), produtos.get(0));
        verify(repository).findByNomeContainingIgnoreCaseOrderByIdAsc("mouse");
        verify(repository, never()).findAll(any(org.springframework.data.domain.Sort.class));
    }

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

    @Test
    void givenProdutoSemEstoqueSuficiente_whenRetirarQuantidade_thenLancarConflito() {
        // Given
        var entity = new ProdutoEntity("Mouse", new BigDecimal("85.00"), 2);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        // When / Then
        assertThrows(EstoqueInsuficienteException.class, () -> service.retirarEstoque(1L, 5));
        assertEquals(2, entity.getQuantidade());
        verify(repository, never()).save(any());
    }

    @Test
    void givenProdutoInexistente_whenBuscar_thenLancarNaoEncontrado() {
        // Given
        when(repository.findById(99L)).thenReturn(Optional.empty());
        // When / Then
        assertThrows(ProdutoNaoEncontradoException.class, () -> service.buscar(99L));
    }
}
