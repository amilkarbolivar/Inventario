package com.inventario.mapper;

import com.inventario.dto.compra.CompraDTO;

import com.inventario.dto.detallecompra.DetalleCompraDTO;
import com.inventario.model.Compra;
import com.inventario.model.Detalle_compra;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CompraMapper {

    public CompraDTO toDTO(Compra compra, List<Detalle_compra> detalles) {
        if (compra == null) return null;

        return CompraDTO.builder()
                .id(compra.getId())
                .fecha(compra.getFecha())
                .administradorId(compra.getAdministrador() != null ?
                        compra.getAdministrador().getId() : null)
                .administradorNombre(compra.getAdministrador() != null ?
                        compra.getAdministrador().getNombre() : null)
                .provedorId(compra.getProvedor() != null ?
                        compra.getProvedor().getId() : null)
                .provedorNombre(compra.getProvedor() != null ?
                        compra.getProvedor().getNombre() : null)
                .supermercadoId(compra.getSupermercado() != null ?
                        compra.getSupermercado().getId() : null)
                .supermercadoNombre(compra.getSupermercado() != null ?
                        compra.getSupermercado().getNombre() : null)
                .tipoPagoId(compra.getTipo_pago() != null ?
                        compra.getTipo_pago().getId() : null)
                .tipoPagoNombre(compra.getTipo_pago() != null ?
                        compra.getTipo_pago().getNombre() : null)
                .detalles(detalles != null ?
                        detalles.stream().map(this::toDetalleDTO).collect(Collectors.toList()) : null)
                .build();
    }

    public DetalleCompraDTO toDetalleDTO(Detalle_compra detalle) {
        if (detalle == null) return null;

        return DetalleCompraDTO.builder()
                .id(detalle.getId())
                .productoId(detalle.getProducto() != null ?
                        detalle.getProducto().getId() : null)
                .productoNombre(detalle.getProducto() != null ?
                        detalle.getProducto().getNombre() : null)
                .cantidad(detalle.getCantidad())
                .precioUnitario(detalle.getPrecioUnitario())
                .subtotal(detalle.getSubtotal())
                .build();
    }
}