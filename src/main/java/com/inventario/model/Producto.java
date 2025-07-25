package com.inventario.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(nullable = false)
    private Integer stock = 0;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "marca_id")
    private Marca marca;

    @ManyToOne
    @JoinColumn(name = "medida_id")
    private Medida medida;

    @ManyToOne
    @JoinColumn(name = "provedor_id")
    private Provedor provedor;

    @Column(nullable = false, unique = true, length = 50)
    private String codigoBarra;

    private Boolean activo;

    @Column(name = "creado_en", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime creadoEn;

    @ManyToOne
    @JoinColumn(name = "supermercado_id")
    private Supermercado supermercado;
}
