package com.inventario.dto;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class AuthResponseDTO {
    private String token;
    private String tipo = "Bearer";
    private AdministradorDTO administrador;
}