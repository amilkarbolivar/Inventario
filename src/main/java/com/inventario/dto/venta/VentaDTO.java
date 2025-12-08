package com.inventario.dto.venta;

import com.inventario.model.Detalle_venta;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import com.inventario.dto.detalleventa.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaDTO {
    private Long id;
    private BigDecimal total;
    private LocalDateTime fecha;

    private Long administradorId;
    private String administradorNombre;

    private Long clienteId;
    private String clienteCedula;

    private Long supermercadoId;
    private String supermercadoNombre;

    private Long tipoPagoId;
    private String tipoPagoNombre;
    private List<DetalleVentaDTO> detalles;
}