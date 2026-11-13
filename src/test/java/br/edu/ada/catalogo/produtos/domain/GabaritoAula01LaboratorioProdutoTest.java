package br.edu.ada.catalogo.produtos.domain;

import br.edu.ada.catalogo.produtos.boundary.ProdutoResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class GabaritoAula01LaboratorioProdutoTest {
    @Test
    void givenProdutoValido_whenConverterParaResponse_thenPreservarCampos() {
        // Given
        Produto produto = new Produto(12L, "Teclado", new BigDecimal("120.00"), 3);
        ProdutoResponse expected = new ProdutoResponse(12L, "Teclado", new BigDecimal("120.00"), 3);
        // When
        ProdutoResponse resultado = ProdutoResponse.from(produto);
        // Then
      /*  assertAll(
                () -> assertEquals(12L, resposta.id()),
                () -> assertEquals("Teclado", resposta.nome()),
                () -> assertEquals(new BigDecimal("120.00"), resposta.preco()),
                () -> assertEquals(3, resposta.quantidade()));*
       */
        assertEquals(expected, resultado);
    }

    @Test
    void givenProdutoInexistente_whenConstruirExcecao_thenInformarIdentificador() {
        // Given
        Long id = 99L;
        // When
        ProdutoNaoEncontradoException erro = new ProdutoNaoEncontradoException(id);
        // Then
        assertTrue(erro.getMessage().contains("99"));
        assertTrue(erro.getMessage().contains("não encontrado"));
    }

    @Test
    void givenProdutoNaoEncontradoException_whenLancarExcecao_thenMensagemProdutoNaoEncontrado() {
        // Given and when
        var resultado = assertThrows(ProdutoNaoEncontradoException.class, () -> {
            throw new ProdutoNaoEncontradoException(10L);
        });

        // Then
        assertEquals("Produto 10 não encontrado.", resultado.getMessage());
    }
}
