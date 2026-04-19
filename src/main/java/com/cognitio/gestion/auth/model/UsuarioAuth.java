package com.cognitio.gestion.auth.model;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cognitio.gestion.usuario.model.Usuario;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuarios_auth", uniqueConstraints = @UniqueConstraint(columnNames = { "tipo_documento",
        "numero_documento" }))
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioAuth implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password; // ✅ corregido

    @Column(name = "numero_documento", nullable = false)
    private String numeroDocumento;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento", nullable = false)
    private TipoDocumento tipoDocumento;

    @Column(nullable = false)
    private boolean activo = true;

    // 🔗 relación con usuario (datos personales)
    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // 🔗 relación con roles (intermedia)
    @OneToMany(mappedBy = "usuarioAuth", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UsuarioRol> roles;

    // ===== SPRING SECURITY =====

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(r -> new SimpleGrantedAuthority(r.getRol().getNombre()))
                .toList();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return activo;
    }
}