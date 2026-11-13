# Catálogo API — Aula 02 (hands-on)

Este projeto Maven é **autónomo e executável**. Foi gerado a partir do ZIP funcional corrigido pelo professor, preservando o `pom.xml`, os Resources, o Spring Security, o H2 e as páginas Thymeleaf.

- **Hands-on dos alunos: questões atuais não resolvidas**.
- Testes de aulas anteriores são mantidos para mostrar regressões. **Não existem testes de aulas futuras**.
- Não foi copiado o arquivo `data/catalogo.mv.db`, que contém dados locais persistidos.
- Pré-requisitos: Java 21, Maven 3.9+; Aula 07 requer Chrome para Selenium.

## Comandos (PowerShell)

```powershell
mvn clean test
mvn spring-boot:run
```

Aplicação: `http://localhost:9091/produtos` | API: `http://localhost:9091/api/produtos`.

Para executar a demonstração **específica desta aula**, consulte `../README.md`. Para o Selenium:

```powershell
mvn test
```

Não execute `demo/` e `hands-on/` ao mesmo tempo na mesma porta. São duas cópias independentes.
