package com.inventario.dto.proveedor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ProveedorUpdateDTO {

    @Size(max = 50, message = "El nombre no puede exceder 50 caracteres")
    private String nombre;

    @Email(message = "El correo debe tener un formato válido")
    @Size(max = 50, message = "El correo no puede exceder 50 caracteres")
    private String correo;

    @Size(max = 50, message = "El teléfono no puede exceder 50 caracteres")
    private String telefono;
}
