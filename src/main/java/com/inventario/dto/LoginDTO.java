package com.inventario.dto;
import lombok.*;
import jakarta.validation.constraints.*;
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class LoginDTO {
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe tener un formato válido")
    private String correo;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    @NotNull(message = "El supermercado es obligatorio")
    private Long supermercadoId;
}
