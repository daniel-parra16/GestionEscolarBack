package com.cognitio.gestion.usuario.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognitio.gestion.usuario.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
}