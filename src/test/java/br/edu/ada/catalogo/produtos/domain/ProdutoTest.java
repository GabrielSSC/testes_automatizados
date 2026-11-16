package br.edu.ada.catalogo.produtos.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProdutoTest {
    @Test
    void givenDadosValidos_whenConstruirProduto_thenPreservarInformacoes() {
        BigDecimal preco = new BigDecimal("85.00");

        Produto produto = new Produto(10L, "Mouse", preco, 8);

        assertAll(
                () -> assertEquals(10L, produto.id()),
                () -> assertEquals("Mouse", produto.nome()),
                () -> assertEquals(preco, produto.preco()),
                () -> assertEquals(8, produto.quantidade()));
    }

    @Test
    void givenProdutosIguais_whenCompararObjetos_thenConsiderarTodosOsCampos() {
        Produto esperado = new Produto(10L, "Mouse", new BigDecimal("85.00"), 8);
        Produto atual = new Produto(10L, "Mouse", new BigDecimal("85.00"), 8);

        assertEquals(esperado, atual);
    }

    @Test
    void givenListasComProdutosIguais_whenCompararListas_thenConsiderarOrdemEElementos() {
        List<Produto> esperados = List.of(
                new Produto(10L, "Mouse", new BigDecimal("85.00"), 8),
                new Produto(20L, "Teclado", new BigDecimal("120.00"), 4));
        List<Produto> atuais = List.of(
                new Produto(10L, "Mouse", new BigDecimal("85.00"), 8),
                new Produto(20L, "Teclado", new BigDecimal("120.00"), 4));

        assertEquals(esperados, atuais);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar criar um produto com preço negativo")
    void givenProdutoComPrecoNegativo_whenCriarUmProduto_thenThrowIllegalArgumentException() {
        IllegalArgumentException resultado = assertThrows(
                IllegalArgumentException.class,
                () -> new Produto(10L, "Mouse", new BigDecimal("-85.00"), 8));

        assertNotNull(resultado);
        assertInstanceOf(IllegalArgumentException.class, resultado);
        assertEquals("O preço não pode ser negativo", resultado.getMessage());
    }
}
