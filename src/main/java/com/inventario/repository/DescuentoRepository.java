package com.inventario.repository;

import com.inventario.model.Descuento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DescuentoRepository extends JpaRepository<Descuento, Long> {

    List<Descuento> findBySupermercadoId(Long supermercadoId);

    Optional<Descuento> findByProductoIdAndSupermercadoIdAndActivoTrue(Long productoId, Long supermercadoId);

    List<Descuento> findBySupermercadoIdAndActivoTrue(Long supermercadoId);

    List<Descuento> findBySupermercadoIdAndProductoIdAndActivoTrue(Long supermercadoId, Long productoId);

    List<Descuento> findByFechaFinBeforeAndActivoTrue(LocalDate fecha);

    List<Descuento> findByFechaInicioAfterAndActivoTrue(LocalDate fecha);

    List<Descuento> findByFechaInicioGreaterThanEqualAndFechaFinLessThanEqualAndActivoTrue(LocalDate desde, LocalDate hasta);

    List<Descuento> findByProductoIdAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqualAndActivoTrue(
            Long productoId,
            LocalDate hoy1,
            LocalDate hoy2
    );

}

