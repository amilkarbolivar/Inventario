package com.inventario.dto.producto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoUpdateDTO {

    @Size(max = 50, message = "El nombre no puede exceder 50 caracteres")
    private String nombre;

    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    @Digits(integer = 10, fraction = 2, message = "El precio debe tener máximo 10 enteros y 2 decimales")
    private BigDecimal precio;

    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    @Size(max = 50, message = "El código de barra no puede exceder 50 caracteres")
    private String codigoBarra;

    private Long categoriaId;

    private Long marcaId;

    private Long medidaId;

    private Long provedorId;

    private Boolean activo;

    private Long supermercadoId;
}