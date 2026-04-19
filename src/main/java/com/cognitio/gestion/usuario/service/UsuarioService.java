package com.cognitio.gestion.usuario.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cognitio.gestion.usuario.model.Usuario;
import com.cognitio.gestion.usuario.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;

    public Usuario save(Usuario usuario) {
        return repository.save(usuario);
    }

    public List<Usuario> findAll() {
        return repository.findAll();
    }

    public Usuario findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
}