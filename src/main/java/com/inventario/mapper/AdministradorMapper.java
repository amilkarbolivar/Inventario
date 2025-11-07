package com.inventario.mapper;
import com.inventario.dto.administrador.AdministradorCreateDTO;
import com.inventario.dto.administrador.AdministradorDTO;
import com.inventario.dto.administrador.AdministradorUpdateDTO;
import com.inventario.model.Administrador;
import org.springframework.stereotype.Component;
@Component
public class AdministradorMapper {


    public AdministradorDTO toDTO(Administrador administrador) {
        if (administrador == null) return null;

        return AdministradorDTO.builder()
                .id(administrador.getId())
                .nombre(administrador.getNombre())
                .correo(administrador.getCorreo())
                .rol(administrador.getRol())
                .activo(administrador.getActivo())
                .creadoEn(administrador.getCreadoEn())
                .supermercadoId(administrador.getSupermercado() != null ?
                        administrador.getSupermercado().getId() : null)
                .supermercadoNombre(administrador.getSupermercado() != null ?
                        administrador.getSupermercado().getNombre() : null)
                .build();
    }

    public Administrador toEntity(AdministradorCreateDTO dto) {
        if (dto == null) return null;

        Administrador administrador = new Administrador();
        administrador.setNombre(dto.getNombre());
        administrador.setCorreo(dto.getCorreo());
        administrador.setPassword(dto.getPassword());
        administrador.setRol(dto.getRol());
        administrador.setActivo(true);
        return administrador;
    }

    public void updateEntity(Administrador administrador, AdministradorUpdateDTO dto) {
        if (dto.getNombre() != null) {
            administrador.setNombre(dto.getNombre());
        }
        if (dto.getCorreo() != null) {
            administrador.setCorreo(dto.getCorreo());
        }
        if (dto.getPassword() != null) {
            administrador.setPassword(dto.getPassword());
        }
        if (dto.getRol() != null) {
            administrador.setRol(dto.getRol());
        }
        if (dto.getActivo() != null) {
            administrador.setActivo(dto.getActivo());
        }
    }
}
