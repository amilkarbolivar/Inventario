package com.inventario.mapper;

import com.inventario.dto.proveedor.ProveedorCreateDTO;
import com.inventario.dto.proveedor.ProveedorDTO;
import com.inventario.dto.proveedor.ProveedorUpdateDTO;
import com.inventario.model.Provedor;
import com.inventario.model.Supermercado;
import org.springframework.stereotype.Component;

@Component
public class ProveedorMapper {

    public ProveedorDTO toDTO(Provedor proveedor) {
        if (proveedor == null) return null;

        return ProveedorDTO.builder()
                .id(proveedor.getId())
                .nombre(proveedor.getNombre())
                .correo(proveedor.getCorreo())
                .telefono(proveedor.getTelefono())
                .supermercadoId(proveedor.getSupermercado() != null ?
                        proveedor.getSupermercado().getId() : null)
                .supermercadoNombre(proveedor.getSupermercado() != null ?
                        proveedor.getSupermercado().getNombre() : null)
                .build();
    }

    public Provedor toEntity(ProveedorCreateDTO dto, Supermercado supermercado) {
        if (dto == null) return null;

        Provedor proveedor = new Provedor();
        proveedor.setNombre(dto.getNombre());
        proveedor.setCorreo(dto.getCorreo());
        proveedor.setTelefono(dto.getTelefono());
        proveedor.setSupermercado(supermercado);
        return proveedor;
    }

    public void updateEntity(Provedor proveedor, ProveedorUpdateDTO dto) {
        if (dto.getNombre() != null) {
            proveedor.setNombre(dto.getNombre());
        }
        if (dto.getCorreo() != null) {
            proveedor.setCorreo(dto.getCorreo());
        }
        if (dto.getTelefono() != null) {
            proveedor.setTelefono(dto.getTelefono());
        }
    }
}