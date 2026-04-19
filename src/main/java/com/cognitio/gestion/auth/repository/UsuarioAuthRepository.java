package com.cognitio.gestion.auth.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognitio.gestion.auth.model.UsuarioAuth;

public interface UsuarioAuthRepository extends JpaRepository<UsuarioAuth, UUID> {
    Optional<UsuarioAuth> findByEmail(String email);
}