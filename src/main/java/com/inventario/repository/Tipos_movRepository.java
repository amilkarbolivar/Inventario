package com.inventario.repository;

import com.inventario.model.Tipos_mov;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Tipos_movRepository extends JpaRepository<Tipos_mov, Long> {
    boolean existsByNombre(String nombre);
}
