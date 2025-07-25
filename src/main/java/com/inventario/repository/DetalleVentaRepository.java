package com.inventario.repository;

import com.inventario.model.Detalle_venta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalleVentaRepository extends JpaRepository<Detalle_venta, Long> {

    // Todos los detalles de ventas en un supermercado
    List<Detalle_venta> findByVentaSupermercadoId(Long supermercadoId);

    // Detalles de un producto vendido en un supermercado
    List<Detalle_venta> findByProductoIdAndVentaSupermercadoId(Long productoId, Long supermercadoId);

    // Detalles de una venta específica en un supermercado
    List<Detalle_venta> findByVentaIdAndVentaSupermercadoId(Long ventaId, Long supermercadoId);

    // Detalles por producto, venta y supermercado (filtro completo)
    List<Detalle_venta> findByVentaIdAndProductoIdAndVentaSupermercadoId(Long ventaId, Long productoId, Long supermercadoId);
}
