package com.inventario.model;
import lombok.*;
import jakarta.persistence.*;
@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Provedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false,length = 50)
    private String nombre;

    @Column(nullable = false,length = 50)
    private String correo;

    @Column(nullable = false,length = 50)
    private String telefono;

    @ManyToOne
    @JoinColumn(name = "supermercado_id", nullable = false)
    private Supermercado supermercado;
}
