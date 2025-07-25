package com.inventario.repository;

import com.inventario.model.Supermercado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SupermercadoRepository extends JpaRepository<Supermercado, Long> {

    Optional<Supermercado> findByNombre(String nombre);
}
