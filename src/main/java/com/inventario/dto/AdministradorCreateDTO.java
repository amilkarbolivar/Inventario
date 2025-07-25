package com.inventario.dto;
import lombok.*;
import jakarta.validation.constraints.*;
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class AdministradorCreateDTO {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede exceder 50 caracteres")
    private String nombre;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe tener un formato válido")
    @Size(max = 100, message = "El correo no puede exceder 100 caracteres")
    private String correo;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    @NotBlank(message = "El rol es obligatorio")
    @Size(max = 50, message = "El rol no puede exceder 50 caracteres")
    private String rol;

    @NotNull(message = "El supermercado es obligatorio")
    private Long supermercadoId;
}
