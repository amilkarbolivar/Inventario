package com.inventario.dto.cliente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateClienteDto {

    @NotBlank
    @Size(max = 50)
    private String documentoTipo;

    @NotBlank
    @Size(max = 25)
    private String cedula;

    @NotNull
    private Long supermercadoId;

}
