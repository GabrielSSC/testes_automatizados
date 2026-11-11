package br.edu.ada.catalogo.produtos.boundary;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoForm {
    @NotBlank
    @Size(max = 120)
    private String nome;
    @NotNull
    @DecimalMin(value = "0.00", inclusive = false)
    private BigDecimal preco;
    @NotNull
    @Min(0)
    private Integer quantidade;
}
