package com.inventario.mapper;

import com.inventario.dto.cliente.CreateClienteDto;
import com.inventario.dto.venta.VentaCreateDTO;
import com.inventario.dto.venta.VentaDTO;

import com.inventario.dto.detalleventa.DetalleVentaDTO;
import com.inventario.model.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VentaMapper {
    private final Detalle_ventaMapper ventamapper;

    public VentaMapper(Detalle_ventaMapper detalleVenta) {
        ventamapper = detalleVenta;
    }

    public VentaDTO toDTO(Venta venta) {
        if (venta == null) return null;

        return VentaDTO.builder()
                .id(venta.getId())
                .total(venta.getTotal())
                .fecha(venta.getFecha())
                .administradorId(venta.getAdministrador() != null ?
                        venta.getAdministrador().getId() : null)
                .administradorNombre(venta.getAdministrador() != null ?
                        venta.getAdministrador().getNombre() : null)
                .clienteId(venta.getCliente() != null ?
                        venta.getCliente().getId() : null)
                .clienteCedula(venta.getCliente() != null ?
                        venta.getCliente().getCedula() : null)
                .supermercadoId(venta.getSupermercado() != null ?
                        venta.getSupermercado().getId() : null)
                .supermercadoNombre(venta.getSupermercado() != null ?
                        venta.getSupermercado().getNombre() : null)
                .tipoPagoId(venta.getTipoPago() != null ?
                        venta.getTipoPago().getId() : null)
                .tipoPagoNombre(venta.getTipoPago() != null ?
                        venta.getTipoPago().getNombre() : null)
                .detalles(
                        venta.getDetalles().stream()
                                .map(ventamapper::toDTO)
                                .collect(Collectors.toList())
                )
                .build();
    }

    public Venta toEntity(VentaCreateDTO dto,
                          Administrador administrador,
                          Cliente cliente,
                          Supermercado supermercado,
                          Tipo_pago tipoPago) {

        if (dto == null) return null;

        Venta venta = new Venta();
        venta.setTotal(dto.getTotal());

        // Relaciones REALES (no entidades fantasmas)
        venta.setAdministrador(administrador);
        venta.setCliente(cliente);
        venta.setSupermercado(supermercado);
        venta.setTipoPago(tipoPago);

        return venta;
    }
}