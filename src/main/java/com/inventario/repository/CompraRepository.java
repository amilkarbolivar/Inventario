package com.inventario.repository;

import com.inventario.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface CompraRepository extends JpaRepository<Compra, Long > {

    List<Compra> findBySupermercadoId(long supermercadoId);

    List<Compra> findByAdministradorIdAndSupermercadoId(long administradorId,long supermercadoId);

    List<Compra> findByFechaBefore(LocalDateTime fecha);

    List<Compra> findByFechaAfter(LocalDateTime fecha);

    List<Compra> findByFechaBetween(LocalDateTime desde, LocalDateTime hasta);

}
