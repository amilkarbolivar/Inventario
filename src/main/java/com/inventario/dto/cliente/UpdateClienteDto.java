package com.inventario.dto.cliente;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateClienteDto {

    @NotBlank
    @Size(max = 50)
    private String documentoTipo;

    @NotBlank
    @Size(max = 25)
    private String cedula;

    // supermercadoId puede ser opcional en actualización; si es null, no se cambia
    private Long supermercadoId;
}
