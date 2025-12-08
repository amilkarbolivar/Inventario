package com.inventario.dto.compra;

import com.inventario.dto.detallecompra.DetalleCompraCreateDTO;
import com.inventario.model.Detalle_compra;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompraCreateDTO {

    @NotNull(message = "El administrador es obligatorio")
    private Long administradorId;

    @NotNull(message = "El proveedor es obligatorio")
    private Long provedorId;

    @NotNull(message = "El supermercado es obligatorio")
    private Long supermercadoId;

    @NotNull(message = "El tipo de pago es obligatorio")
    private Long tipoPagoId;

    @NotNull(message = "debe haber un total")
    private BigDecimal total;
    private List<DetalleCompraCreateDTO> detalles;
}