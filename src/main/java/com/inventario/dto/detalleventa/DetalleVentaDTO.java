package com.inventario.dto.detalleventa;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleVentaDTO {
    private Long id;
    private Long productoId;
    private Long ventaId;
    private String productoNombre;
    private Integer cantidad;
    private BigDecimal precioDetalle;
    private BigDecimal subtotal;
    private BigDecimal descuentoAplicado;
}