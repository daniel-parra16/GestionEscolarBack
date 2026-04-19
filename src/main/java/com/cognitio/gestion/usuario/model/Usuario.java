package com.cognitio.gestion.usuario.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.cognitio.gestion.auth.model.UsuarioAuth;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private Boolean activo = true; // 🔥 te faltaba esto

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    // 🔗 relación inversa (NO cargar siempre)
    @OneToOne(mappedBy = "usuario", fetch = FetchType.LAZY)
    @JsonIgnore
    private UsuarioAuth auth;
}