package br.edu.ada.catalogo.platform;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.stereotype.Component;

@Component("catalogoReadiness")
public class CatalogoReadinessHealth implements HealthIndicator {
    @Value("${catalogo.nome}")
    private String nome;

    @Override
    public Health health() {
        return nome == null || nome.isBlank() ? Health.down().build() : Health.up().build();
    }
}
