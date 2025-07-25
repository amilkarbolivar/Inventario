package com.inventario.model;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter  @Setter
@NoArgsConstructor @AllArgsConstructor
public class Detalle_venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id ;

    @ManyToOne
    @JoinColumn(name = "venta_id",nullable = false)
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(name = "precio_detal", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioDetalle;

    @Column(precision = 10, scale = 2)
    private BigDecimal subtotal;

    @PrePersist
    public void calcularSubtotal() {
        if (cantidad != null && precioDetalle != null) {
            this.subtotal = precioDetalle.multiply(BigDecimal.valueOf(cantidad));
        }
    }
}
