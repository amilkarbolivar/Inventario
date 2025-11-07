package com.inventario.dto.supermercado;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class SupermercadoDTO {
    private long id;
    private String correo;
    private String nombre;
    private LocalDateTime fecha;
}
