
package com.inventario.dto;
import lombok.*;
import jakarta.validation.constraints.*;
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class AdministradorUpdateDTO {
    @Size(max = 50, message = "El nombre no puede exceder 50 caracteres")
    private String nombre;

    @Email(message = "El correo debe tener un formato válido")
    @Size(max = 100, message = "El correo no puede exceder 100 caracteres")
    private String correo;

    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    @Size(max = 50, message = "El rol no puede exceder 50 caracteres")
    private String rol;

    private Boolean activo;
}