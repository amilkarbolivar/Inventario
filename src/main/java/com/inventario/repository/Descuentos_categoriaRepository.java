package com.inventario.repository;

import com.inventario.model.Descuento_categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface Descuentos_categoriaRepository extends JpaRepository<Descuento_categoria,Long> {

    List<Descuento_categoria> findBySupermercadoIdAndActivoTrue(Long supermercadoId);

    List<Descuento_categoria> findBySupermercadoId(Long supermercadoId);

    List<Descuento_categoria> findBySupermercadoIdAndCategoriaIdAndActivoTrue(Long supermercadoId, Long categoriaId);

    List<Descuento_categoria> findByCategoriaIdAndSupermercadoIdAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqualAndActivoTrue(
            Long categoriaId,
            Long supermercadoId,
            LocalDate hoy1,
            LocalDate hoy2
    );
}
