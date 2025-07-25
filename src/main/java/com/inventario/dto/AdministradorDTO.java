package com.inventario.dto;
import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class AdministradorDTO {
    private Long id;
    private String nombre;
    private String correo;
    private String rol;
    private Boolean activo;
    private LocalDateTime creadoEn;
    private Long supermercadoId;
    private String supermercadoNombre;
}
