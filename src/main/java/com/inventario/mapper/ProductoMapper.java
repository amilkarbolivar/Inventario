package com.inventario.mapper;

import com.inventario.dto.producto.ProductoCreateDTO;
import com.inventario.dto.producto.ProductoDTO;
import com.inventario.dto.producto.ProductoUpdateDTO;
import com.inventario.model.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    public ProductoDTO toDTO(Producto producto) {
        if (producto == null) return null;

        return ProductoDTO.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .precio(producto.getPrecio())
                .stock(producto.getStock())
                .codigoBarra(producto.getCodigoBarra())
                .activo(producto.getActivo())
                .creadoEn(producto.getCreadoEn())
                .categoriaId(producto.getCategoria() != null ? producto.getCategoria().getId() : null)
                .categoriaNombre(producto.getCategoria() != null ? producto.getCategoria().getNombre() : null)
                .marcaId(producto.getMarca() != null ? producto.getMarca().getId() : null)
                .marcaNombre(producto.getMarca() != null ? producto.getMarca().getNombre() : null)
                .medidaId(producto.getMedida() != null ? producto.getMedida().getId() : null)
                .medidaUnidad(producto.getMedida() != null ? producto.getMedida().getUnidad() : null)
                .provedorId(producto.getProvedor() != null ? producto.getProvedor().getId() : null)
                .provedorNombre(producto.getProvedor() != null ? producto.getProvedor().getNombre() : null)
                .supermercadoId(producto.getSupermercado() != null ? producto.getSupermercado().getId() : null)
                .supermercadoNombre(producto.getSupermercado() != null ? producto.getSupermercado().getNombre() : null)
                .build();
    }

    public Producto toEntity(ProductoCreateDTO dto) {
        if (dto == null) return null;

        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setCodigoBarra(dto.getCodigoBarra());
        producto.setActivo(true);
        return producto;
    }

    public void updateEntity(Producto producto, ProductoUpdateDTO dto) {
        if (dto.getNombre() != null) {
            producto.setNombre(dto.getNombre());
        }
        if (dto.getPrecio() != null) {
            producto.setPrecio(dto.getPrecio());
        }
        if (dto.getStock() != null) {
            producto.setStock(dto.getStock());
        }
        if (dto.getCodigoBarra() != null) {
            producto.setCodigoBarra(dto.getCodigoBarra());
        }
        if (dto.getActivo() != null) {
            producto.setActivo(dto.getActivo());
        }
    }
}