package com.inventario.repository;

import com.inventario.model.Detalle_compra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalleCompraRepository extends JpaRepository<Detalle_compra, Long> {

    // Obtener todos los detalles de una compra específica
    List<Detalle_compra> findByCompraId(Long compraId);

    List<Detalle_compra> findByCompraSupermercadoId(Long supermercadoId);

    // Obtener todos los detalles de un producto específico
    List<Detalle_compra> findByProductoId(Long productoId);

    // Buscar por compra y producto (para evitar duplicados o consultar uno específico)
    List<Detalle_compra> findByCompraIdAndProductoId(Long compraId, Long productoId);
}
