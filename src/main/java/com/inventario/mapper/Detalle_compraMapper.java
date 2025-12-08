package com.inventario.mapper;

import com.inventario.dto.cliente.CreateClienteDto;
import com.inventario.dto.detallecompra.DetalleCompraCreateDTO;
import com.inventario.dto.detallecompra.DetalleCompraDTO;
import com.inventario.dto.detalleventa.DetalleVentaCreateDTO;
import com.inventario.model.Compra;
import com.inventario.model.Detalle_compra;
import com.inventario.model.Producto;
import org.springframework.stereotype.Component;


import java.math.BigDecimal;

@Component
public class Detalle_compraMapper {

    public DetalleCompraDTO toDO(Detalle_compra detalle_compra){
        if(detalle_compra==null) return null;

        return  DetalleCompraDTO.builder()
                .id(detalle_compra.getId())
                .precioUnitario(detalle_compra.getPrecioUnitario())
                .productoId(detalle_compra.getProducto().getId())
                .productoNombre(detalle_compra.getProducto().getNombre())
                .subtotal(detalle_compra.getSubtotal())
                .cantidad(detalle_compra.getCantidad())
                .build();
    }

    public Detalle_compra toEntity(DetalleCompraCreateDTO dto,
                                   Compra compra,
                                   Producto producto) {

        if (dto == null) return null;

        Detalle_compra detalle = new Detalle_compra();
        detalle.setCantidad(dto.getCantidad());
        detalle.setPrecioUnitario(dto.getPrecioUnitario());

        // Subtotal calculado


        // Relaciones REALES (no fantasma)
        detalle.setCompra(compra);
        detalle.setProducto(producto);

        return detalle;
    }
}
