package com.inventario.repository;

import com.inventario.model.Tipos_mov;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Tipos_movRepository extends JpaRepository<Tipos_mov, Long> {

    Optional<Tipos_mov> findByNombre(String nombre);

    boolean existsByNombre(String nombre);
}
