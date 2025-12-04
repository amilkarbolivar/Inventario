package com.inventario.dto.venta;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import com.inventario.dto.detalleventa.DetalleVentaCreateDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaCreateDTO {

    @NotNull(message = "El administrador es obligatorio")
    private Long administradorId;

    private Long clienteId; // Opcional para ventas sin cliente registrado

    @NotNull(message = "El supermercado es obligatorio")
    private Long supermercadoId;

    @NotNull(message = "El tipo de pago es obligatorio")
    private Long tipoPagoId;

   @NotNull(message = "el total es obligatorio")
    private BigDecimal total;
}