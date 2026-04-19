package com.cognitio.gestion.auth.service;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cognitio.gestion.auth.dto.AuthResponse;
import com.cognitio.gestion.auth.dto.LoginRequest;
import com.cognitio.gestion.auth.dto.RefreshRequest;
import com.cognitio.gestion.auth.dto.RegisterRequest;
import com.cognitio.gestion.auth.mapper.AuthMapper;
import com.cognitio.gestion.auth.model.Rol;
import com.cognitio.gestion.auth.model.UsuarioAuth;
import com.cognitio.gestion.auth.model.UsuarioRol;
import com.cognitio.gestion.auth.repository.RolRepository;
import com.cognitio.gestion.auth.repository.UsuarioAuthRepository;
import com.cognitio.gestion.auth.repository.UsuarioRolRepository;
import com.cognitio.gestion.auth.security.JwtService;
import com.cognitio.gestion.usuario.model.Usuario;
import com.cognitio.gestion.usuario.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;

    private final UsuarioAuthRepository authRepo;
    private final UsuarioRepository usuarioRepo;
    private final RolRepository rolRepo;
    private final UsuarioRolRepository usuarioRolRepo;

    private final AuthMapper mapper;

    // 🔐 LOGIN
    public AuthResponse login(LoginRequest request) {

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        UsuarioAuth user = authRepo.findByEmail(request.getEmail())
                .orElseThrow();

        return buildTokens(user);
    }

    // 🧾 REGISTER
    public AuthResponse register(RegisterRequest request) {

        // validar duplicados
        if (authRepo.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email ya existe");
        }

        // 1. usuario
        Usuario usuario = mapper.toUsuario(request);
        usuario.setActivo(true);
        usuarioRepo.save(usuario);

        // 2. auth
        UsuarioAuth auth = mapper.toUsuarioAuth(request);
        auth.setPassword(encoder.encode(request.getPassword()));
        usuario.setActivo(true);
        auth.setUsuario(usuario);

        authRepo.save(auth);

        // 3. roles
        List<UsuarioRol> roles = request.getRoles().stream()
                .map(nombre -> {
                    Rol rol = rolRepo.findByNombre(nombre)
                            .orElseThrow();

                    return UsuarioRol.builder()
                            .usuarioAuth(auth)
                            .rol(rol)
                            .build();
                }).toList();

        usuarioRolRepo.saveAll(roles);
        auth.setRoles(roles);

        return buildTokens(auth);
    }

    // 🔄 REFRESH
    public AuthResponse refresh(RefreshRequest request) {

        String username = jwtService.extractUsername(request.getRefreshToken());

        UsuarioAuth user = authRepo.findByEmail(username)
                .orElseThrow();

        if (!jwtService.isTokenValid(request.getRefreshToken(), user.getEmail())) {
            throw new RuntimeException("Token inválido");
        }

        return AuthResponse.builder()
                .accessToken(jwtService.generateAccessToken(user.getEmail()))
                .refreshToken(request.getRefreshToken())
                .build();
    }

    // 🔥 helper
    private AuthResponse buildTokens(UsuarioAuth user) {
        return AuthResponse.builder()
                .accessToken(jwtService.generateAccessToken(user.getEmail()))
                .refreshToken(jwtService.generateRefreshToken(user.getEmail()))
                .build();
    }
}