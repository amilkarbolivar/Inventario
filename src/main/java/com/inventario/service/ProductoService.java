package com.inventario.service;

import com.inventario.dto.producto.ProductoCreateDTO;
import com.inventario.dto.producto.ProductoDTO;
import com.inventario.dto.producto.ProductoUpdateDTO;
import com.inventario.exception.*;
import com.inventario.mapper.ProductoMapper;
import com.inventario.model.*;
import com.inventario.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final SupermercadoRepository supermercadoRepository;
    private final CategoriaRepository categoriaRepository;
    private final MarcaRepository marcaRepository;
    private final MedidasRepository medidasRepository;
    private final ProvedorRepositorio provedorRepositorio;
    private final ProductoMapper mapper;

    public ProductoDTO crear(ProductoCreateDTO dto) {
        Supermercado supermercado = supermercadoRepository.findById(dto.getSupermercadoId())
                .orElseThrow(() -> new SupermercadoNotFoundExepcion("Supermercado no encontrado"));

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new ProductoNotFoundException("Categoría no encontrada"));

        if (!categoria.getSupermercado().getId().equals(dto.getSupermercadoId())) {
            throw new ProductoNotFoundException("La categoría no pertenece al supermercado especificado");
        }

        Marca marca = marcaRepository.findById(dto.getMarcaId())
                .orElseThrow(() -> new ProductoNotFoundException("Marca no encontrada"));

        if (!marca.getSupermercado().getId().equals(dto.getSupermercadoId())) {
            throw new ProductoNotFoundException("La marca no pertenece al supermercado especificado");
        }

        Medida medida = medidasRepository.findById(dto.getMedidaId())
                .orElseThrow(() -> new ProductoNotFoundException("Medida no encontrada"));

        Provedor provedor = provedorRepositorio.findById(dto.getProvedorId())
                .orElseThrow(() -> new ProductoNotFoundException("Proveedor no encontrado"));

        if (!provedor.getSupermercado().getId().equals(dto.getSupermercadoId())) {
            throw new ProductoNotFoundException("El proveedor no pertenece al supermercado especificado");
        }

        Producto producto = mapper.toEntity(dto);
        producto.setSupermercado(supermercado);
        producto.setCategoria(categoria);
        producto.setMarca(marca);
        producto.setMedida(medida);
        producto.setProvedor(provedor);
        producto.setCreadoEn(LocalDateTime.now());

        Producto savedProducto = productoRepository.save(producto);
        return mapper.toDTO(savedProducto);
    }

    @Transactional(readOnly = true)
    public ProductoDTO obtenerPorId(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado"));
        return mapper.toDTO(producto);
    }

    @Transactional(readOnly = true)
    public List<ProductoDTO> obtenerActivosPorSupermercado(Long supermercadoId) {
        if (!supermercadoRepository.existsById(supermercadoId)) {
            throw new SupermercadoNotFoundExepcion("Supermercado no encontrado");
        }

        return productoRepository.findBySupermercadoIdAndActivoTrue(supermercadoId)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductoDTO> obtenerPorMarca(Long supermercadoId, Long marcaId) {
        if (!supermercadoRepository.existsById(supermercadoId)) {
            throw new SupermercadoNotFoundExepcion("Supermercado no encontrado");
        }

        if (!marcaRepository.existsById(marcaId)) {
            throw new ProductoNotFoundException("Marca no encontrada");
        }

        return productoRepository.findBySupermercadoIdAndMarcaId(supermercadoId, marcaId)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductoDTO> obtenerPorProveedor(Long supermercadoId, Long provedorId) {
        if (!supermercadoRepository.existsById(supermercadoId)) {
            throw new SupermercadoNotFoundExepcion("Supermercado no encontrado");
        }

        if (!provedorRepositorio.existsById(provedorId)) {
            throw new ProductoNotFoundException("Proveedor no encontrado");
        }

        return productoRepository.findBySupermercadoIdAndProvedorIdAndActivoTrue(supermercadoId, provedorId)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public ProductoDTO actualizar(Long id, ProductoUpdateDTO dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado"));

        if (dto.getCategoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                    .orElseThrow(() -> new ProductoNotFoundException("Categoría no encontrada"));

            if (!categoria.getSupermercado().getId().equals(producto.getSupermercado().getId())) {
                throw new ProductoNotFoundException("La categoría no pertenece al supermercado del producto");
            }
            producto.setCategoria(categoria);
        }

        if (dto.getMarcaId() != null) {
            Marca marca = marcaRepository.findById(dto.getMarcaId())
                    .orElseThrow(() -> new ProductoNotFoundException("Marca no encontrada"));

            if (!marca.getSupermercado().getId().equals(producto.getSupermercado().getId())) {
                throw new ProductoNotFoundException("La marca no pertenece al supermercado del producto");
            }
            producto.setMarca(marca);
        }

        if (dto.getMedidaId() != null) {
            Medida medida = medidasRepository.findById(dto.getMedidaId())
                    .orElseThrow(() -> new ProductoNotFoundException("Medida no encontrada"));
            producto.setMedida(medida);
        }

        if (dto.getProvedorId() != null) {
            Provedor provedor = provedorRepositorio.findById(dto.getProvedorId())
                    .orElseThrow(() -> new ProductoNotFoundException("Proveedor no encontrado"));

            if (!provedor.getSupermercado().getId().equals(producto.getSupermercado().getId())) {
                throw new ProductoNotFoundException("El proveedor no pertenece al supermercado del producto");
            }
            producto.setProvedor(provedor);
        }

        mapper.updateEntity(producto, dto);
        Producto updatedProducto = productoRepository.save(producto);
        return mapper.toDTO(updatedProducto);
    }

    public void eliminar(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new ProductoNotFoundException("Producto no encontrado");
        }
        productoRepository.deleteById(id);
    }

    public ProductoDTO desactivar(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado"));

        producto.setActivo(false);
        Producto updatedProducto = productoRepository.save(producto);
        return mapper.toDTO(updatedProducto);
    }

    public ProductoDTO activar(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado"));

        producto.setActivo(true);
        Producto updatedProducto = productoRepository.save(producto);
        return mapper.toDTO(updatedProducto);
    }

    public ProductoDTO actualizarStock(Long id, Integer cantidad) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado"));

        if (cantidad < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa");
        }

        producto.setStock(cantidad);
        Producto updatedProducto = productoRepository.save(producto);
        return mapper.toDTO(updatedProducto);
    }
}