package br.edu.ada.catalogo.produtos.domain;

import br.edu.ada.catalogo.produtos.boundary.ProdutoResponse;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class ProdutoPrimeirosPassosLaboratorioTest {
    @Test
    void givenProdutoValido_whenConverterParaResponse_thenPreservarCampos() {
        // Given: crie um Produto com nome, valor e quantidade.
        Produto produto = new Produto(1L, "Caneta", new BigDecimal("10.50"), 100);

        // When: execute ProdutoResponse.from(produto).
        ProdutoResponse response = ProdutoResponse.from(produto);

        // Then: compare todos os atributos esperados.
        assertEquals(1L, response.id());
        assertEquals("Caneta", response.nome());
        assertEquals(new BigDecimal("10.50"), response.preco());
        assertEquals(100, response.quantidade());
    }

    @Test
    void givenProdutoInexistente_whenConstruirExcecao_thenInformarIdentificador() {
        // Given/When: instancie ProdutoNaoEncontradoException.
        Long idEsperado = 42L;
        ProdutoNaoEncontradoException excecao = new ProdutoNaoEncontradoException(idEsperado);

        // Then: verifique se a mensagem informa o identificador solicitado.
        assertTrue(excecao.getMessage().contains("42"));
    }
}
