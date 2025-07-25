package com.inventario.model;
import lombok.*;
import jakarta.persistence.*;
import org.springframework.expression.spel.ast.NullLiteral;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Administrador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false,length = 255)
    private String password;

    @Column(nullable = false, length = 100)
    private String correo;

    @Column(nullable = false, length = 50)
    private String rol;

    @Column(nullable = false)
    private Boolean activo = true;

    @Column(name = "creado_en", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime creadoEn;

    @ManyToOne
    @JoinColumn(name = "supermercado_id")
    private Supermercado supermercado;
}
