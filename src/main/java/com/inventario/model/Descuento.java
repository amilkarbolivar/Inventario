package com.inventario.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Descuento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal porcentaje;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @Column(nullable = false)
    private Boolean activo = true;

    @ManyToOne
    @JoinColumn(name = "administrador_id",nullable = false)
    private Administrador administrador;

    @ManyToOne
    @JoinColumn(name = "supermercado_id",nullable = false)
    private Supermercado supermercado;
}
