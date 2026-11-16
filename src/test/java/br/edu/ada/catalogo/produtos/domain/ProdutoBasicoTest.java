package br.edu.ada.catalogo.produtos.domain;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class ProdutoBasicoTest {
    @Test
    void givenDadosValidos_whenConstruirProduto_thenPreservarInformacoes() {
        // Given
        var preco = new BigDecimal("85.00");
        // When
        var produto = new Produto(10L, "Mouse", preco, 8);
        // Then
        assertAll(
                () -> assertEquals(10L, produto.id()),
                () -> assertEquals("Mouse", produto.nome()),
                () -> assertEquals(preco, produto.preco()),
                () -> assertEquals(8, produto.quantidade()));
    }
}
