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

    private Supermercado obtenerSupermercado(Long id) {
        return supermercadoRepository.findById(id)
                .orElseThrow(() -> new SupermercadoNotFoundExepcion("Supermercado no encontrado"));
    }

    private Categoria obtenerCategoria(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Categoría no encontrada"));
    }

    private Marca obtenerMarca(Long id) {
        return marcaRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Marca no encontrada"));
    }

    private Medida obtenerMedida(Long id) {
        return medidasRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Medida no encontrada"));
    }

    private Provedor obtenerProveedor(Long id) {
        return provedorRepositorio.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Proveedor no encontrado"));
    }



    public ProductoDTO crear(ProductoCreateDTO dto) {

        Supermercado supermercado = obtenerSupermercado(dto.getSupermercadoId());

        Categoria categoria = obtenerCategoria(dto.getCategoriaId());
        if (!categoria.getSupermercado().getId().equals(supermercado.getId()))
            throw new ProductoNotFoundException("La categoría no pertenece al supermercado");

        Marca marca = obtenerMarca(dto.getMarcaId());
        if (!marca.getSupermercado().getId().equals(supermercado.getId()))
            throw new ProductoNotFoundException("La marca no pertenece al supermercado");

        Medida medida = obtenerMedida(dto.getMedidaId());

        Provedor provedor = obtenerProveedor(dto.getProvedorId());
        if (!provedor.getSupermercado().getId().equals(supermercado.getId()))
            throw new ProductoNotFoundException("El proveedor no pertenece al supermercado");


        Producto producto = mapper.toEntity(dto, categoria, marca, medida, provedor, supermercado);


        return mapper.toDTO(productoRepository.save(producto));
    }

    @Transactional(readOnly = true)
    public ProductoDTO obtenerPorId(Long id) {
        return mapper.toDTO(productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado")));
    }



    @Transactional(readOnly = true)
    public List<ProductoDTO> obtenerActivosPorSupermercado(Long supermercadoId) {

        if (!supermercadoRepository.existsById(supermercadoId))
            throw new SupermercadoNotFoundExepcion("Supermercado no encontrado");

        return productoRepository.findBySupermercadoIdAndActivoTrue(supermercadoId)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public List<ProductoDTO> obtenerPorMarca(Long supermercadoId, Long marcaId) {

        if (!supermercadoRepository.existsById(supermercadoId))
            throw new SupermercadoNotFoundExepcion("Supermercado no encontrado");

        if (!marcaRepository.existsById(marcaId))
            throw new ProductoNotFoundException("Marca no encontrada");

        return productoRepository.findBySupermercadoIdAndMarcaId(supermercadoId, marcaId)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public List<ProductoDTO> obtenerPorProveedor(Long supermercadoId, Long provedorId) {

        if (!supermercadoRepository.existsById(supermercadoId))
            throw new SupermercadoNotFoundExepcion("Supermercado no encontrado");

        if (!provedorRepositorio.existsById(provedorId))
            throw new ProductoNotFoundException("Proveedor no encontrado");

        return productoRepository.findBySupermercadoIdAndProvedorIdAndActivoTrue(supermercadoId, provedorId)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }


    public ProductoDTO actualizar(Long id, ProductoUpdateDTO dto) {

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado"));

        Categoria categoria = null;
        Marca marca = null;
        Medida medida = null;
        Provedor provedor = null;
       Supermercado nuevoSupermercado = null;
        if (dto.getCategoriaId() != null) {
            categoria = obtenerCategoria(dto.getCategoriaId());
            if (!categoria.getSupermercado().getId().equals(producto.getSupermercado().getId()))
                throw new ProductoNotFoundException("La categoría no pertenece al supermercado del producto");
        }

        if (dto.getSupermercadoId() != null) {
             nuevoSupermercado = obtenerSupermercado(dto.getSupermercadoId());
        }

            if (dto.getMarcaId() != null) {
            marca = obtenerMarca(dto.getMarcaId());
            if (!marca.getSupermercado().getId().equals(producto.getSupermercado().getId()))
                throw new ProductoNotFoundException("La marca no pertenece al supermercado del producto");
        }

        if (dto.getMedidaId() != null)
            medida = obtenerMedida(dto.getMedidaId());

        if (dto.getProvedorId() != null) {
            provedor = obtenerProveedor(dto.getProvedorId());
            if (!provedor.getSupermercado().getId().equals(producto.getSupermercado().getId()))
                throw new ProductoNotFoundException("El proveedor no pertenece al supermercado del producto");
        }

        // mapper PRO
        mapper.updateEntity(producto, dto, categoria, marca, medida, provedor,nuevoSupermercado);

        return mapper.toDTO(productoRepository.save(producto));
    }


    public void eliminar(Long id) {
        if (!productoRepository.existsById(id))
            throw new ProductoNotFoundException("Producto no encontrado");

        productoRepository.deleteById(id);
    }

    // ----------------------------
    //     ACTIVAR / DESACTIVAR
    // ----------------------------

    public ProductoDTO desactivar(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado"));

        producto.setActivo(false);
        return mapper.toDTO(productoRepository.save(producto));
    }

    public ProductoDTO activar(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado"));

        producto.setActivo(true);
        return mapper.toDTO(productoRepository.save(producto));
    }



    public ProductoDTO actualizarStock(Long id, Integer cantidad) {

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado"));

        if (cantidad < 0)
            throw new IllegalArgumentException("La cantidad no puede ser negativa");

        producto.setStock(cantidad);
        return mapper.toDTO(productoRepository.save(producto));
    }
}
