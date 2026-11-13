package br.edu.ada.catalogo.produtos.domain;

import br.edu.ada.catalogo.produtos.repository.ProdutoEntity;
import br.edu.ada.catalogo.produtos.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Test
    void givenProdutoId_whenBuscarProdutoPorId_thenProdutoDeveSerRetornado() {
        //given
        Long produtoId = 1L;

        //mocando o objeto repository
        ProdutoRepository repository = Mockito.mock(ProdutoRepository.class);

        //defino o objeto que será retornado pelo mock quando o método findById for chamado
        ProdutoEntity produtoMocado = new ProdutoEntity("Produto 1", new BigDecimal(10), 12);
        produtoMocado.setId(produtoId);
        //mocar o comportamento
        when(repository.findById(1L)).thenReturn(Optional.of(produtoMocado));

        ProdutoService produtoService = new ProdutoService(repository);

        //when
        Produto resultado = produtoService.buscar(produtoId);

        //then
        assertNotNull(resultado);
        assertEquals(produtoId, resultado.id());
    }

    @Test
    void givenProdutoIdQueNaoExiste_whenBuscarProdutoPorId_thenProdutoDeveSerRetornado() {
        //given
        Long produtoId = 1L;

        //mocando o objeto repository
        ProdutoRepository repository = Mockito.mock(ProdutoRepository.class);

        //defino o objeto que será retornado pelo mock quando o método findById for chamado
        ProdutoEntity produtoMocado = new ProdutoEntity("Produto 1", new BigDecimal(10), 12);
        produtoMocado.setId(produtoId);
        //mocar o comportamento
        when(repository.findById(1L)).thenReturn(Optional.empty());

        ProdutoService produtoService = new ProdutoService(repository);

        //when
        var resultado = assertThrows(ProdutoNaoEncontradoException.class, () ->produtoService.buscar(produtoId));

        //then
        assertEquals("Produto 1 não encontrado.", resultado.getMessage());
    }
}