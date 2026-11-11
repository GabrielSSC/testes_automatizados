package br.edu.ada.catalogo.security.repository;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 80)
    private String username;
    @Column(name = "senha_hash", nullable = false, length = 100)
    private String senhaHash;
    @Column(nullable = false, length = 120)
    private String roles;

    public UsuarioEntity(String username, String senhaHash, String roles) {
        this.username = username;
        this.senhaHash = senhaHash;
        this.roles = roles;
    }
}
