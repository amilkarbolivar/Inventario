package com.inventario.repository;

import com.inventario.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {

    // Todas las ventas de un supermercado
    List<Venta> findBySupermercadoId(Long supermercadoId);

    // Ventas por administrador en un supermercado
    List<Venta> findByAdministradorIdAndSupermercadoId(Long administradorId, Long supermercadoId);

    // Ventas por cliente en un supermercado
    List<Venta> findByClienteIdAndSupermercadoId(Long clienteId, Long supermercadoId);

    // Ventas por fecha exacta en un supermercado
    List<Venta> findByFechaAndSupermercadoId(LocalDateTime fecha, Long supermercadoId);

    // Ventas antes de una fecha en un supermercado
    List<Venta> findByFechaBeforeAndSupermercadoId(LocalDateTime fecha, Long supermercadoId);

    // Ventas después de una fecha en un supermercado
    List<Venta> findByFechaAfterAndSupermercadoId(LocalDateTime fecha, Long supermercadoId);

    // Ventas entre fechas en un supermercado
    List<Venta> findByFechaBetweenAndSupermercadoId(LocalDateTime desde, LocalDateTime hasta, Long supermercadoId);

    // Ventas por tipo de pago en un supermercado
    List<Venta> findByTipoPagoIdAndSupermercadoId(Long tipoPagoId, Long supermercadoId);
}
