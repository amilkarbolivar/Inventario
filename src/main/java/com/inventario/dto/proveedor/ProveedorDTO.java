package com.inventario.dto.proveedor;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ProveedorDTO {

    private Long id;
    private String nombre;
    private String correo;
    private String telefono;
    private Long supermercadoId;
    private String supermercadoNombre;
}
