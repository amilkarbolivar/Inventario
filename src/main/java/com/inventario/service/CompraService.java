package com.inventario.service;

import com.inventario.dto.compra.CompraCreateDTO;
import com.inventario.dto.compra.CompraDTO;
import com.inventario.dto.compra.DetalleCompraCreateDTO;
import com.inventario.exception.*;
import com.inventario.mapper.CompraMapper;
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
public class CompraService {

    private final CompraRepository compraRepository;
    private final DetalleCompraRepository detalleCompraRepository;
    private final AdministradorRepository administradorRepository;
    private final ProvedorRepositorio provedorRepositorio;
    private final SupermercadoRepository supermercadoRepository;
    private final Tipo_pagoRepository tipoPagoRepository;
    private final ProductoRepository productoRepository;
    private final CompraMapper mapper;

    public CompraDTO crear(CompraCreateDTO dto) {
        // Verificar que el supermercado existe
        Supermercado supermercado = supermercadoRepository.findById(dto.getSupermercadoId())
                .orElseThrow(() -> new SupermercadoNotFoundExepcion("Supermercado no encontrado"));

        // Verificar que el administrador existe y pertenece al supermercado
        Administrador administrador = administradorRepository.findById(dto.getAdministradorId())
                .orElseThrow(() -> new AdministradorNotFoundException("Administrador no encontrado"));

        if (!administrador.getSupermercado().getId().equals(dto.getSupermercadoId())) {
            throw new AdministradorNotFoundException("El administrador no pertenece al supermercado especificado");
        }

        // Verificar que el proveedor existe y pertenece al supermercado
        Provedor provedor = provedorRepositorio.findById(dto.getProvedorId())
                .orElseThrow(() -> new CompraNotFoundException("Proveedor no encontrado"));

        if (!provedor.getSupermercado().getId().equals(dto.getSupermercadoId())) {
            throw new CompraNotFoundException("El proveedor no pertenece al supermercado especificado");
        }

        // Verificar que el tipo de pago existe
        Tipo_pago tipoPago = tipoPagoRepository.findById(dto.getTipoPagoId())
                .orElseThrow(() -> new CompraNotFoundException("Tipo de pago no encontrado"));

        // Crear la compra
        Compra compra = new Compra();
        compra.setAdministrador(administrador);
        compra.setProvedor(provedor);
        compra.setSupermercado(supermercado);
        compra.setTipo_pago(tipoPago);
        compra.setFecha(LocalDateTime.now());

        Compra savedCompra = compraRepository.save(compra);

        // Crear los detalles y actualizar stock
        List<Detalle_compra> detalles = dto.getDetalles().stream().map(detalleDTO -> {
            Producto producto = productoRepository.findById(detalleDTO.getProductoId())
                    .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado: " + detalleDTO.getProductoId()));

            // Verificar que el producto pertenece al supermercado
            if (!producto.getSupermercado().getId().equals(dto.getSupermercadoId())) {
                throw new ProductoNotFoundException("El producto no pertenece al supermercado especificado");
            }

            // Actualizar stock del producto
            producto.setStock(producto.getStock() + detalleDTO.getCantidad());
            productoRepository.save(producto);

            // Crear detalle de compra
            Detalle_compra detalle = new Detalle_compra();
            detalle.setCompra(savedCompra);
            detalle.setProducto(producto);
            detalle.setCantidad(detalleDTO.getCantidad());
            detalle.setPrecioUnitario(detalleDTO.getPrecioUnitario());

            return detalleCompraRepository.save(detalle);
        }).collect(Collectors.toList());

        return mapper.toDTO(savedCompra, detalles);
    }

    @Transactional(readOnly = true)
    public CompraDTO obtenerPorId(Long id) {
        Compra compra = compraRepository.findById(id)
                .orElseThrow(() -> new CompraNotFoundException("Compra no encontrada"));

        List<Detalle_compra> detalles = detalleCompraRepository.findByCompraId(id);

        return mapper.toDTO(compra, detalles);
    }

    @Transactional(readOnly = true)
    public List<CompraDTO> obtenerPorSupermercado(Long supermercadoId) {
        // Verificar que el supermercado existe
        if (!supermercadoRepository.existsById(supermercadoId)) {
            throw new SupermercadoNotFoundExepcion("Supermercado no encontrado");
        }

        return compraRepository.findBySupermercadoId(supermercadoId)
                .stream()
                .map(compra -> {
                    List<Detalle_compra> detalles = detalleCompraRepository.findByCompraId(compra.getId());
                    return mapper.toDTO(compra, detalles);
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CompraDTO> obtenerPorAdministrador(Long administradorId, Long supermercadoId) {
        // Verificar que el supermercado existe
        if (!supermercadoRepository.existsById(supermercadoId)) {
            throw new SupermercadoNotFoundExepcion("Supermercado no encontrado");
        }

        // Verificar que el administrador existe
        if (!administradorRepository.existsById(administradorId)) {
            throw new AdministradorNotFoundException("Administrador no encontrado");
        }

        return compraRepository.findByAdministradorIdAndSupermercadoId(administradorId, supermercadoId)
                .stream()
                .map(compra -> {
                    List<Detalle_compra> detalles = detalleCompraRepository.findByCompraId(compra.getId());
                    return mapper.toDTO(compra, detalles);
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CompraDTO> obtenerPorFechas(LocalDateTime desde, LocalDateTime hasta) {
        return compraRepository.findByFechaBetween(desde, hasta)
                .stream()
                .map(compra -> {
                    List<Detalle_compra> detalles = detalleCompraRepository.findByCompraId(compra.getId());
                    return mapper.toDTO(compra, detalles);
                })
                .collect(Collectors.toList());
    }

    public void eliminar(Long id) {
        if (!compraRepository.existsById(id)) {
            throw new CompraNotFoundException("Compra no encontrada");
        }

        // Eliminar primero los detalles
        List<Detalle_compra> detalles = detalleCompraRepository.findByCompraId(id);
        detalles.forEach(detalle -> {
            // Restar el stock que se agregó en la compra
            Producto producto = detalle.getProducto();
            producto.setStock(producto.getStock() - detalle.getCantidad());
            productoRepository.save(producto);

            detalleCompraRepository.delete(detalle);
        });

        compraRepository.deleteById(id);
    }
}