package com.inventario.dto.compra;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.List;
import com.inventario.dto.compra.DetalleCompraCreateDTO;

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

    @NotEmpty(message = "Debe incluir al menos un detalle de compra")
    @Valid
    private List<DetalleCompraCreateDTO> detalles;
}