package com.cognitio.gestion.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognitio.gestion.auth.model.UsuarioRol;

public interface UsuarioRolRepository extends JpaRepository<UsuarioRol, Long> {
}