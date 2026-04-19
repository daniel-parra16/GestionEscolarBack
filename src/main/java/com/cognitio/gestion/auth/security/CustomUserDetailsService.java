package com.cognitio.gestion.auth.security;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.cognitio.gestion.auth.model.UsuarioAuth;
import com.cognitio.gestion.auth.repository.UsuarioAuthRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioAuthRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UsuarioAuth user = repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return user;
    }
}