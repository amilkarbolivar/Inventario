package com.inventario.repository;

import com.inventario.model.Movimientos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimientos,Long> {

    List<Movimientos> findBySupermercadoId(Long supermercadoId);

    List<Movimientos> findByAdministradorIdAndSupermercadoId(Long supermercadoId, Long administradorId);

    List<Movimientos> findByProductoIdAndSupermercadoId(Long productoId, Long supermercadoId);

    List<Movimientos> findByTipoMovIdAndSupermercadoId(Long tipoMovId, Long supermercadoId);

}
