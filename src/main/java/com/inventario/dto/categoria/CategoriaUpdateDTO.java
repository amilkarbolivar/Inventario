package com.inventario.dto.categoria;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoriaUpdateDTO {

    @Size(max = 50, message = "El nombre no puede exceder 50 caracteres")
    private String nombre;

    private String descripcion;
}