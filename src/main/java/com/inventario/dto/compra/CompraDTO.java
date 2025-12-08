package com.inventario.dto.compra;

import com.inventario.dto.detallecompra.DetalleCompraDTO;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompraDTO {
    private Long id;
    private LocalDateTime fecha;

    private Long administradorId;
    private String administradorNombre;

    private Long provedorId;
    private String provedorNombre;

    private Long supermercadoId;
    private String supermercadoNombre;

    private Long tipoPagoId;
    private String tipoPagoNombre;

    private BigDecimal total;
    private List<DetalleCompraDTO> detalle;
}