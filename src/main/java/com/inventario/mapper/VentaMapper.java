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
                .build();
    }

    public Venta toEntity(VentaCreateDTO dto) {

        if (dto == null) return null;

        Venta venta = new Venta();
        venta.setTotal(dto.getTotal());

        // Fecha la debes generar más arriba en el servicio
        // venta.setFecha(LocalDateTime.now());

        // ====== ADMINISTRADOR ======
        if (dto.getAdministradorId() != null) {
            Administrador admin = new Administrador();
            admin.setId(dto.getAdministradorId());
            venta.setAdministrador(admin);
        }

        // ====== CLIENTE ======
        if (dto.getClienteId() != null) {
            Cliente cliente = new Cliente();
            cliente.setId(dto.getClienteId());
            venta.setCliente(cliente);
        }

        // ====== SUPERMERCADO ======
        if (dto.getSupermercadoId() != null) {
            Supermercado supermercado = new Supermercado();
            supermercado.setId(dto.getSupermercadoId());
            venta.setSupermercado(supermercado);
        }

        // ====== TIPO DE PAGO ======
        if (dto.getTipoPagoId() != null) {
            Tipo_pago tipoPago = new Tipo_pago();
            tipoPago.setId(dto.getTipoPagoId());
            venta.setTipoPago(tipoPago);
        }

        return venta;
    }
}