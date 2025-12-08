package com.inventario.mapper;

import com.inventario.dto.compra.CompraCreateDTO;
import com.inventario.dto.compra.CompraDTO;


import com.inventario.model.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CompraMapper {
            private final Detalle_compraMapper compramapper;

    public CompraMapper(Detalle_compraMapper compramapper) {
        this.compramapper = compramapper;
    }

    public CompraDTO toDTO(Compra compra) {
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
                .detalle(
                        compra.getDetalles().stream()
                                .map(compramapper::toDO)
                                .collect(Collectors.toList())
                )
                .build();
    }

    public Compra toEntity(CompraCreateDTO dto,
                           Administrador admin,
                           Provedor provedor,
                           Supermercado supermercado,
                           Tipo_pago tipoPago) {

        Compra compra = new Compra();
        compra.setTotal(dto.getTotal());
        compra.setAdministrador(admin);
        compra.setProvedor(provedor);
        compra.setSupermercado(supermercado);
        compra.setTipo_pago(tipoPago);

        return compra;
    }

}
