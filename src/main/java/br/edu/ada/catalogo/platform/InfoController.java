package br.edu.ada.catalogo.platform;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {
    @Value("${catalogo.nome}")
    private String nome;

    @GetMapping("/api/info")
    public Informacoes info() {
        return new Informacoes(nome, "microservico");
    }

    public record Informacoes(String nome, String tipo) {
    }
}
