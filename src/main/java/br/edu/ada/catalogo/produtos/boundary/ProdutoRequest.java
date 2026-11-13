package br.edu.ada.catalogo.produtos.boundary;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProdutoRequest(@NotBlank @Size(max = 120) String nome,
        @NotNull @DecimalMin(value = "0.00", inclusive = false) @Digits(integer = 8, fraction = 2) BigDecimal preco,
        @NotNull @Min(0) Integer quantidade) {
}
