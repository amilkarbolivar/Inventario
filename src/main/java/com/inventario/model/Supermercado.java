

package com.inventario.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Supermercado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 50)
    private String correo;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(name = "fecha", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private LocalDateTime fecha;

    @PrePersist
    public void prePersist() {
        this.fecha = LocalDateTime.now();
    }
}
