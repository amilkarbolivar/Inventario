package com.inventario.mapper;

import com.inventario.dto.categoria.CategoriaCreateDTO;
import com.inventario.dto.categoria.CategoriaDTO;
import com.inventario.dto.categoria.CategoriaUpdateDTO;
import com.inventario.model.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {

    public CategoriaDTO toDTO(Categoria categoria) {
        if (categoria == null) return null;

        return CategoriaDTO.builder()
                .id(categoria.getId())
                .nombre(categoria.getNombre())
                .descripcion(categoria.getDescripcion())
                .supermercadoId(categoria.getSupermercado() != null ?
                        categoria.getSupermercado().getId() : null)
                .supermercadoNombre(categoria.getSupermercado() != null ?
                        categoria.getSupermercado().getNombre() : null)
                .build();
    }

    public Categoria toEntity(CategoriaCreateDTO dto) {
        if (dto == null) return null;

        Categoria categoria = new Categoria();
        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());
        return categoria;
    }

    public void updateEntity(Categoria categoria, CategoriaUpdateDTO dto) {
        if (dto.getNombre() != null) {
            categoria.setNombre(dto.getNombre());
        }
        if (dto.getDescripcion() != null) {
            categoria.setDescripcion(dto.getDescripcion());
        }
    }
}