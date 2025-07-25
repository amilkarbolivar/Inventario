package com.inventario.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "supermercado_id")
    private Supermercado supermercado;
}
