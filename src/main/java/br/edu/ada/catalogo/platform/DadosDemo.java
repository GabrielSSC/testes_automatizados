package br.edu.ada.catalogo.platform;

import br.edu.ada.catalogo.produtos.repository.ProdutoEntity;
import br.edu.ada.catalogo.produtos.repository.ProdutoRepository;
import br.edu.ada.catalogo.security.repository.UsuarioEntity;
import br.edu.ada.catalogo.security.repository.UsuarioRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.math.BigDecimal;

@Configuration
public class DadosDemo {
    // Apenas curso/local: contas pré-definidas são INSEGURAS para produção.
    @Bean
    ApplicationRunner popular(UsuarioRepository usuarios, ProdutoRepository produtos, PasswordEncoder encoder) {
        return args -> {
            if (usuarios.findByUsername("admin").isEmpty())
                usuarios.save(new UsuarioEntity("admin", encoder.encode("Aula07@123"), "ROLE_ADMIN,ROLE_USER"));
            if (usuarios.findByUsername("aluno").isEmpty())
                usuarios.save(new UsuarioEntity("aluno", encoder.encode("Aula07@123"), "ROLE_USER"));
            if (produtos.count() == 0) {
                produtos.save(new ProdutoEntity("Notebook", new BigDecimal("3500.00"), 10));
                produtos.save(new ProdutoEntity("Mouse", new BigDecimal("85.00"), 25));
                produtos.save(new ProdutoEntity("Teclado", new BigDecimal("180.00"), 0));
            }
        };
    }
}
