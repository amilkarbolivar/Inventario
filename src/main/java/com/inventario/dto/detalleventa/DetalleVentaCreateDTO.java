package com.inventario.dto.detalleventa;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleVentaCreateDTO {

    @NotNull(message = "El producto es obligatorio")
    private Long productoId;
    @NotNull(message = "El precio es obligatorio")
    private BigDecimal precioDetalle;
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidad;
}