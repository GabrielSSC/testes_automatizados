package br.edu.ada.catalogo.produtos.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoBasicoTest {
    @BeforeEach
    void prepararCenario() {
        System.out.println("BeforeEach: preparando o cenário do teste.");
    }

    @AfterEach
    void finalizarCenario() {
        System.out.println("AfterEach: finalizando o cenário do teste.");
    }

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

    @Test
    void givenProdutosIguais_whenCompararObjetos_thenConsiderarTodosOsCampos() {
        // Given
        var esperado = new Produto(10L, "Mouse", new BigDecimal("85.00"), 8);
        var atual = new Produto(10L, "Mouse", new BigDecimal("85.00"), 8);

        // When / Then
        assertEquals(esperado, atual);
    }

    @Test
    void givenListasComProdutosIguais_whenCompararListas_thenConsiderarOrdemEElementos() {
        // Given
        var esperados = List.of(
                new Produto(10L, "Mouse", new BigDecimal("85.00"), 8),
                new Produto(20L, "Teclado", new BigDecimal("120.00"), 4));
        var atuais = List.of(
                new Produto(10L, "Mouse", new BigDecimal("85.00"), 8),
                new Produto(20L, "Teclado", new BigDecimal("120.00"), 4));

        // When / Then
        assertEquals(esperados, atuais);
    }
}
