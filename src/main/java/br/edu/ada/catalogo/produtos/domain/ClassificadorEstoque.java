package br.edu.ada.catalogo.produtos.domain;

/** LABORATORIO: primeiro escreva os testes, depois implemente a regra. */
public class ClassificadorEstoque {
    public String classificar(int quantidade) {
        if (quantidade < 0) {
            throw new IllegalArgumentException("A quantidade não pode ser negativa");
        }
        if (quantidade == 0) {
            return "ESGOTADO";
        }
        if (quantidade <= 5) {
            return "BAIXO";
        }
        return "NORMAL";
    }
}
