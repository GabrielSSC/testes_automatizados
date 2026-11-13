package br.edu.ada.catalogo.produtos.boundary;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RetiradaRequest(@NotNull @Positive Integer quantidade) {
}
