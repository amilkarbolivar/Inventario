package com.inventario.model;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "administrador_id", nullable = false)
    private Administrador administrador;

    @ManyToOne
    @JoinColumn(name = "proveedor_id",nullable = false)
    private Provedor provedor;

    @ManyToOne
    @JoinColumn(name = "supermercado_id",nullable = false)
    private Supermercado supermercado;

    @ManyToOne
    @JoinColumn(name = "tipo_pago_id",nullable = false)
    private Tipo_pago tipo_pago;

    @PrePersist
    public void setFecha() {
        if (fecha == null) {
            this.fecha = LocalDateTime.now();
        }
    }
}
