package br.edu.ada.catalogo.produtos.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClassificadorEstoqueTest {
    private ClassificadorEstoque classificador;

    @BeforeEach
    void setUp() {
        classificador = new ClassificadorEstoque();
    }

    @Test
    void givenQuantidadeZero_whenClassificar_thenRetornarEsgotado() {
        // given
        int quantidade = 0;

        // when
        String resultado = classificador.classificar(quantidade);

        // then
        assertEquals("ESGOTADO", resultado);
    }

    @Test
    void givenQuantidadeNegativa_whenClassificar_thenLancarExcecao() {
        assertThrows(IllegalArgumentException.class, () -> classificador.classificar(-1));
    }

    @Test
    void givenQuantidadeNoLimiteBaixo_whenClassificar_thenRetornarBaixo() {
        assertEquals("BAIXO", classificador.classificar(1));
        assertEquals("BAIXO", classificador.classificar(5));
    }

    @Test
    void givenQuantidadeAcimaDoLimiteBaixo_whenClassificar_thenRetornarNormal() {
        assertEquals("NORMAL", classificador.classificar(6));
    }
}
