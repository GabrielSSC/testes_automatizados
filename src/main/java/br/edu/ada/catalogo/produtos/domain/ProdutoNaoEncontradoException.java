package br.edu.ada.catalogo.produtos.domain;

public class ProdutoNaoEncontradoException extends RuntimeException {
    public ProdutoNaoEncontradoException(Long id) {
        super("Produto " + id + " não encontrado.");
    }
}
