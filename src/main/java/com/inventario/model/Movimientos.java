package com.inventario.model;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Movimientos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "producto_id",nullable = false)
    private Producto producto;

    @Column(name = "fecha", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "tipo_mov_id", nullable = false)
    private Tipos_mov tipoMov;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String motivo;

    @ManyToOne
    @JoinColumn(name = "administrador_id", nullable = false)
    private Administrador administrador;

    @ManyToOne
    @JoinColumn(name = "supermercado_id", nullable = false)
    private Supermercado supermercado;

    @PrePersist
    public void setFecha() {
        if (fecha == null) {
            this.fecha = LocalDateTime.now();
        }
    }
}
