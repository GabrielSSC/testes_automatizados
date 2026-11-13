package br.edu.ada.catalogo.produtos.domain;

public class EstoqueInsuficienteException extends RuntimeException {
    public EstoqueInsuficienteException(int disponivel, int solicitado) {
        super("Estoque insuficiente: disponível=" + disponivel + ", solicitado=" + solicitado + ".");
    }
}
