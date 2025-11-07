package com.inventario.mapper;

import com.inventario.dto.cliente.CreateClienteDto;
import com.inventario.dto.cliente.UpdateClienteDto;
import com.inventario.dto.cliente.ClienteDTO;
import com.inventario.model.Cliente;
import com.inventario.model.Supermercado;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public Cliente toEntity(CreateClienteDto dto, Supermercado supermercado) {
        Cliente c = new Cliente();
        // tu entidad usa nombres con guion bajo: documento_tipo
        c.setDocumento_tipo(dto.getDocumentoTipo());
        c.setCedula(dto.getCedula());
        c.setSupermercado(supermercado);
        return c;
    }

    /**
     * Actualiza la entidad existente. Si supermercado != null, reemplaza la relación.
     * Si supermercado == null, mantiene la relación actual.
     */
    public void updateEntity(UpdateClienteDto dto, Cliente cliente, Supermercado supermercado) {
        cliente.setDocumento_tipo(dto.getDocumentoTipo());
        cliente.setCedula(dto.getCedula());
        if (supermercado != null) {
            cliente.setSupermercado(supermercado);
        }
    }

    public ClienteDTO toDto(Cliente c) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(c.getId());
        dto.setDocumento_tipo(c.getDocumento_tipo());
        dto.setCedula(c.getCedula());
        dto.setSupermercadoId(c.getSupermercado() != null ? c.getSupermercado().getId() : null);
        return dto;
    }


}
