package com.inventario.service;

import com.inventario.dto.compra.CompraCreateDTO;
import com.inventario.dto.compra.CompraDTO;
import com.inventario.dto.detallecompra.DetalleCompraCreateDTO;
import com.inventario.exception.*;
import com.inventario.mapper.CompraMapper;
import com.inventario.model.*;
import com.inventario.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.inventario.mapper.Detalle_compraMapper;

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
    private final Detalle_compraMapper detalleCompraMapper;
    private final Tipos_movRepository tipos;
    private final MovimientoRepository movimiento;

    @Transactional
    public CompraDTO crear(CompraCreateDTO dto) {

        // ===== VALIDAR SUPERMERCADO =====
        Supermercado supermercado = supermercadoRepository.findById(dto.getSupermercadoId())
                .orElseThrow(() -> new SupermercadoNotFoundExepcion("Supermercado no encontrado"));

        // ===== VALIDAR ADMINISTRADOR =====
        Administrador administrador = administradorRepository.findById(dto.getAdministradorId())
                .orElseThrow(() -> new AdministradorNotFoundException("Administrador no encontrado"));

        if (!administrador.getSupermercado().getId().equals(dto.getSupermercadoId())) {
            throw new AdministradorNotFoundException("El administrador no pertenece a este supermercado");
        }

        // ===== VALIDAR PROVEEDOR =====
        Provedor provedor = provedorRepositorio.findById(dto.getProvedorId())
                .orElseThrow(() -> new CompraNotFoundException("Proveedor no encontrado"));

        if (!provedor.getSupermercado().getId().equals(dto.getSupermercadoId())) {
            throw new CompraNotFoundException("El proveedor no pertenece a este supermercado");
        }

        // ===== VALIDAR TIPO DE PAGO =====
        Tipo_pago tipoPago = tipoPagoRepository.findById(dto.getTipoPagoId())
                .orElseThrow(() -> new CompraNotFoundException("Tipo de pago no encontrado"));

        // ===== CREAR COMPRA =====
        Compra compra = mapper.toEntity(dto, administrador, provedor, supermercado, tipoPago);

        // ===== PROCESAR DETALLES =====
        for (DetalleCompraCreateDTO deto : dto.getDetalles()) {

            Producto producto = productoRepository.findById(deto.getProductoId())
                    .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado"));

            producto.setStock(producto.getStock() + deto.getCantidad());
            productoRepository.save(producto);

            Movimientos mov = new Movimientos();
            mov.setProducto(producto);
            Tipos_mov tipoMov = tipos.findByNombre("ENTRADA")
                    .orElseThrow(() -> new RuntimeException("Tipo de movimiento SALIDA no existe"));


            mov.setMotivo("venta");
            mov.setTipoMov(tipoMov);

            mov.setCantidad(deto.getCantidad());
            mov.setAdministrador(administrador);
            mov.setSupermercado(supermercado);

            movimiento.save(mov);

            // ---- Mapear usando ENTIDADES REALES ----
            Detalle_compra detalle = detalleCompraMapper.toEntity(deto,compra,producto);

            // ---- Agregar detalle a la compra ----
            compra.getDetalles().add(detalle);
        }

        // 🚀 Gracias a CascadeType.ALL, esto guarda compra + detalles
        Compra saved = compraRepository.save(compra);

        return mapper.toDTO(saved);
    }



    @Transactional(readOnly = true)
    public CompraDTO obtenerPorId(Long id) {
        Compra compra = compraRepository.findById(id)
                .orElseThrow(() -> new CompraNotFoundException("Compra no encontrada"));

        return mapper.toDTO(compra);
    }

    @Transactional(readOnly = true)
    public List<CompraDTO> obtenerPorSupermercado(Long supermercadoId) {
        // Verificar que el supermercado existe
        if (!supermercadoRepository.existsById(supermercadoId)) {
            throw new SupermercadoNotFoundExepcion("Supermercado no encontrado");
        }

        return compraRepository.findBySupermercadoId(supermercadoId)
                .stream()
                .map(mapper::toDTO)
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
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CompraDTO> obtenerPorFechas(LocalDateTime desde, LocalDateTime hasta) {
        return compraRepository.findByFechaBetween(desde, hasta)
                .stream()
                .map(mapper ::toDTO)
                .collect(Collectors.toList());
    }

    public void eliminar(Long id) {
        if (!compraRepository.existsById(id)) {
            throw new CompraNotFoundException("Compra no encontrada");
        }

        compraRepository.deleteById(id);
    }
}