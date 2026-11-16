package br.edu.ada.catalogo.produtos.boundary;

import br.edu.ada.catalogo.produtos.domain.Produto;
import br.edu.ada.catalogo.produtos.domain.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.math.BigDecimal;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Aula 03: controller isolado, SEM Spring Boot context, filtros ou banco.
class ProdutoRestControllerTest {
    ProdutoService service;
    MockMvc mvc;

    @BeforeEach
    void preparar() {
        service = mock(ProdutoService.class);
        mvc = MockMvcBuilders.standaloneSetup(new ProdutoRestController(service)).build();
    }

    @Test
    void givenProdutosExistentes_whenGetColecao_thenResponderJsonComStatus200() throws Exception {
        // Given
        when(service.listar(null)).thenReturn(List.of(new Produto(1L, "Mouse", new BigDecimal("85.00"), 8)));
        // When / Then
        mvc.perform(get("/api/produtos")).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].nome").value("Mouse"))
                .andExpect(jsonPath("$[0].preco").value("85.0"))
                .andExpect(jsonPath("$[0].quantidade").value(8))
                .andExpect(jsonPath("$[0].id").value(1));
        verify(service).listar(null);
    }

    @Test
    void givenNomeInformado_whenGetColecao_thenFiltrarProdutosPeloNome() throws Exception {
        when(service.listar("mouse")).thenReturn(List.of(new Produto(1L, "Mouse", new BigDecimal("85.00"), 8)));

        mvc.perform(get("/api/produtos").param("nome", "mouse"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Mouse"));

        verify(service).listar("mouse");
    }

    @Test
    void givenProdutoExistente_whenBuscarPorId_thenResponderProdutoComStatus200() throws Exception {
        when(service.buscar(1L)).thenReturn(new Produto(1L, "Mouse", new BigDecimal("85.00"), 8));

        mvc.perform(get("/api/produtos/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Mouse"))
                .andExpect(jsonPath("$.quantidade").value(8));

        verify(service).buscar(1L);
    }

    @Test
    void givenAdministradorEProdutoValido_whenCadastrar_thenResponder201ComLocation() throws Exception {
        // Given
        var produto = new Produto(42L, "Livro", new BigDecimal("25.00"), 4);
        when(service.criar(eq("Livro"), eq(new BigDecimal("25.00")), eq(4))).thenReturn(produto);
        // When / Then: MockMvc standalone testa o controller, não a cadeia do Spring Security.
        mvc.perform(post("/api/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Livro\",\"preco\":25.00,\"quantidade\":4}"))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(content().json("""
                    {
                      "id": 42,
                      "nome": "Livro",
                      "preco": 25.00,
                      "quantidade": 4
                    }
                    """));
        verify(service).criar("Livro", new BigDecimal("25.00"), 4);
    }

    @Test
    void givenProdutoExistenteEAtualizacaoValida_whenAtualizar_thenResponderProdutoAtualizado() throws Exception {
        var produtoAtualizado = new Produto(1L, "Mouse sem fio", new BigDecimal("99.90"), 6);
        when(service.atualizar(1L, "Mouse sem fio", new BigDecimal("99.90"), 6)).thenReturn(produtoAtualizado);

        mvc.perform(put("/api/produtos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Mouse sem fio\",\"preco\":99.90,\"quantidade\":6}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Mouse sem fio"))
                .andExpect(jsonPath("$.preco").value(99.9))
                .andExpect(jsonPath("$.quantidade").value(6));

        verify(service).atualizar(1L, "Mouse sem fio", new BigDecimal("99.90"), 6);
    }

    @Test
    void givenProdutoExistente_whenExcluir_thenResponderSemConteudo() throws Exception {
        mvc.perform(delete("/api/produtos/1"))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        verify(service).excluir(1L);
    }

    @Test
    void givenEstoqueEQuantidadeDeRetiradaValidos_whenRetirar_thenResponderProdutoComEstoqueAtualizado()
            throws Exception {
        when(service.retirarEstoque(1L, 3)).thenReturn(new Produto(1L, "Mouse", new BigDecimal("85.00"), 5));

        mvc.perform(post("/api/produtos/1/retiradas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"quantidade\":3}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.quantidade").value(5));

        verify(service).retirarEstoque(1L, 3);
    }
}
