package com.inventario.repository;

import com.inventario.model.Tipo_pago;
import org.springframework.data.jpa.repository.JpaRepository;


public interface Tipo_pagoRepository extends JpaRepository<Tipo_pago, Long> {

    // Validar si existe uno con ese nombre (global)
    boolean existsByNombre(String nombre);
}
