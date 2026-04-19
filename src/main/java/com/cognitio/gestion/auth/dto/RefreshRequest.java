package com.cognitio.gestion.auth.dto;

import lombok.Data;

@Data
public class RefreshRequest {
    private String refreshToken;
}