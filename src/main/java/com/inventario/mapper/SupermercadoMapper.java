package com.inventario.mapper;

import com.inventario.dto.supermercado.CreateSupermercadoDTO;
import com.inventario.dto.supermercado.SupermercadoDTO;
import com.inventario.dto.supermercado.SupermercadoUpdateDTO;
import com.inventario.model.Supermercado;
import org.springframework.stereotype.Component;

@Component
public class SupermercadoMapper {

    public SupermercadoDTO toDTO(Supermercado supermercado){

        if(supermercado == null) return null;

        return SupermercadoDTO.builder()
                .id(supermercado.getId())
                .correo(supermercado.getCorreo())
                .nombre(supermercado.getNombre())
                .fecha(supermercado.getFecha())
                .build();
    }

    public Supermercado toEntity(CreateSupermercadoDTO  dto){
            if(dto == null) return null ;

            Supermercado supermercado = new Supermercado();
            supermercado.setNombre(dto.getNombre());
            supermercado.setCorreo(dto.getCorreo());
            return supermercado;
    }
    public Supermercado toEntityupdate(SupermercadoUpdateDTO  dto){
        if(dto == null) return null ;

        Supermercado supermercado = new Supermercado();
        supermercado.setNombre(dto.getNombre());
        supermercado.setCorreo(dto.getCorreo());
        return supermercado;
    }

    public void updateSupermercado (Supermercado supermercado, SupermercadoUpdateDTO dto){
        if(dto.getNombre()!=null){
            supermercado.setNombre(dto.getNombre());
        }
        if(dto.getCorreo()!=null){
            supermercado.setCorreo(dto.getCorreo());
        }
    }
}
