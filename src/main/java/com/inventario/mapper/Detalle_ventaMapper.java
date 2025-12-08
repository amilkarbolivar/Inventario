package com.inventario.mapper;

import com.inventario.dto.detalleventa.DetalleVentaDTO;
import com.inventario.dto.detalleventa.DetalleVentaCreateDTO;
import com.inventario.model.Detalle_venta;
import com.inventario.model.Producto;
import com.inventario.model.Venta;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class Detalle_ventaMapper {


    public  DetalleVentaDTO toDTO(Detalle_venta d) {
        if (d == null) return null;

        return DetalleVentaDTO.builder()
                .id(d.getId())
                .productoId(d.getProducto().getId())
                .productoNombre(d.getProducto().getNombre())
                .cantidad(d.getCantidad())
                .precioDetalle(d.getPrecioDetalle())
                .subtotal(d.getSubtotal())
                .build();
    }

    // ---- CREATE DTO → ENTIDAD ----

    public Detalle_venta toEntity(DetalleVentaCreateDTO dto,
                                  Producto producto,
                                  Venta venta) {

        if (dto == null) return null;

        Detalle_venta detalle = new Detalle_venta();
        detalle.setCantidad(dto.getCantidad());
        detalle.setPrecioDetalle(dto.getPrecioDetalle());

        // El subtotal lo calcula @PrePersist, no lo calculamos aquí.
        // detalle.setSubtotal(...);

        detalle.setProducto(producto);
        detalle.setVenta(venta);

        return detalle;
    }



}
