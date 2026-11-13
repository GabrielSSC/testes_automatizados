# Estoque Local (opcional)

Servidor Java 21 puro, preservado do curso de Quarkus. É utilizado para demonstrar REST Client e fallback.

No PowerShell, na raiz do projeto:

```powershell
java --class-path estoque-local/src estoque-local/src/EstoqueLocalServer.java
```

A API responde em `http://localhost:8081/products`. Inicie o Spring Boot em outro terminal e execute `GET http://localhost:9091/products`. Se desligar o Estoque Local, a API devolve HTTP 503 com `origem: FALLBACK`.
