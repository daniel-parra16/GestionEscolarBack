package com.cognitio.gestion.auth.dto;

import java.util.List;

import com.cognitio.gestion.auth.model.TipoDocumento;

import lombok.Data;

@Data
public class RegisterRequest {

    private String email;
    private String password;

    private String numeroDocumento;
    private TipoDocumento tipoDocumento;

    private String firstName;
    private String lastName;

    private List<String> roles;
}