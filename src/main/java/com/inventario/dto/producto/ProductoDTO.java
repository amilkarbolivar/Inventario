package com.inventario.dto.producto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoDTO {
    private Long id;
    private String nombre;
    private BigDecimal precio;
    private Integer stock;
    private String codigoBarra;
    private Boolean activo;
    private LocalDateTime creadoEn;

    private Long categoriaId;
    private String categoriaNombre;

    private Long marcaId;
    private String marcaNombre;

    private Long medidaId;
    private String medidaUnidad;

    private Long provedorId;
    private String provedorNombre;

    private Long supermercadoId;
    private String supermercadoNombre;
}