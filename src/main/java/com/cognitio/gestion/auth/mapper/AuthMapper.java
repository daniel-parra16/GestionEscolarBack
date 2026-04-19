package com.cognitio.gestion.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cognitio.gestion.auth.dto.RegisterRequest;
import com.cognitio.gestion.auth.model.UsuarioAuth;
import com.cognitio.gestion.usuario.model.Usuario;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "activo", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    UsuarioAuth toUsuarioAuth(RegisterRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "activo", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "auth", ignore = true)
    Usuario toUsuario(RegisterRequest request);
}