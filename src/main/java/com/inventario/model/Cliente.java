package com.inventario.model;
import io.micrometer.common.lang.Nullable;
import lombok.*;
import jakarta.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 50)
    private String documento_tipo;

    @Column(nullable = false, length = 25, unique = true)
    private String cedula;

    @ManyToOne
    @JoinColumn(name = "supermercado_id")
    private Supermercado supermercado;
}
