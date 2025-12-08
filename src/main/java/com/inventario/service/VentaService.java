package com.inventario.service;

import com.inventario.dto.venta.VentaCreateDTO;
import com.inventario.dto.venta.VentaDTO;
import com.inventario.dto.detalleventa.DetalleVentaCreateDTO;
import com.inventario.exception.*;
import com.inventario.mapper.VentaMapper;
import com.inventario.model.*;
import com.inventario.mapper.Detalle_ventaMapper;
import com.inventario.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.inventario.repository.Tipos_movRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class VentaService {

    private final VentaRepository ventaRepository;
    private final DetalleVentaRepository detalleVentaRepository;
    private final AdministradorRepository administradorRepository;
    private final ClienteRepository clienteRepository;
    private final SupermercadoRepository supermercadoRepository;
    private final Tipo_pagoRepository tipoPagoRepository;
    private final ProductoRepository productoRepository;
    private final DescuentoRepository descuentoRepository;
    private final Descuentos_categoriaRepository descuentosCategoriaRepository;
    private final VentaMapper mapper;
    private final Detalle_ventaMapper detalleVentaMapper;
    private final Descuentos_categoriaRepository categoriadescuentos;
    private final Tipos_movRepository tipos;
    private final MovimientoRepository movimiento;

    @Transactional
    public VentaDTO crear(VentaCreateDTO dto) {

        // ===== VALIDAR SUPERMERCADO =====
        Supermercado supermercado = supermercadoRepository.findById(dto.getSupermercadoId())
                .orElseThrow(() -> new SupermercadoNotFoundExepcion("Supermercado no encontrado"));

        // ===== VALIDAR ADMINISTRADOR =====
        Administrador administrador = administradorRepository.findById(dto.getAdministradorId())
                .orElseThrow(() -> new AdministradorNotFoundException("Administrador no encontrado"));

        if (!administrador.getSupermercado().getId().equals(dto.getSupermercadoId())) {
            throw new AdministradorNotFoundException("El administrador no pertenece al supermercado");
        }

        // ===== VALIDAR CLIENTE (opcional) =====
        Cliente cliente = null;
        if (dto.getClienteId() != null) {
            cliente = clienteRepository.findById(dto.getClienteId())
                    .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado"));

            if (!cliente.getSupermercado().getId().equals(dto.getSupermercadoId())) {
                throw new ClienteNotFoundException("El cliente no pertenece al supermercado");
            }
        }

        // ===== VALIDAR TIPO DE PAGO =====
        Tipo_pago tipoPago = tipoPagoRepository.findById(dto.getTipoPagoId())
                .orElseThrow(() -> new VentaNotFoundException("Tipo de pago no encontrado"));

        // ===== CREAR VENTA PADRE =====
        Venta venta = new Venta();
        venta.setAdministrador(administrador);
        venta.setCliente(cliente);
        venta.setSupermercado(supermercado);
        venta.setTipoPago(tipoPago);
        venta.setFecha(LocalDateTime.now());
        venta.setTotal(BigDecimal.ZERO);

        BigDecimal totalVenta = BigDecimal.ZERO;

        // ===== PROCESAR DETALLES =====
        for (DetalleVentaCreateDTO deto : dto.getDetalles()) {

            // 1. Validar producto
            Producto producto = productoRepository.findById(deto.getProductoId())
                    .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado"));

            LocalDate hoy = LocalDate.now();

            Movimientos mov = new Movimientos();
            mov.setProducto(producto);
            Tipos_mov tipoMov = tipos.findByNombre("SALIDA")
                    .orElseThrow(() -> new RuntimeException("Tipo de movimiento SALIDA no existe"));


            mov.setMotivo("venta");
            mov.setTipoMov(tipoMov);

            mov.setCantidad(deto.getCantidad());
            mov.setAdministrador(administrador);
            mov.setSupermercado(supermercado);

            movimiento.save(mov);
            // 2. Buscar descuentos activos
            BigDecimal descuentoProducto = descuentoRepository
                    .findTopByProductoIdAndActivoTrueAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(
                            producto.getId(), hoy, hoy
                    )
                    .map(Descuento::getPorcentaje)
                    .orElse(BigDecimal.ZERO);

            BigDecimal descuentoCategoria = categoriadescuentos
                    .findTopByCategoriaIdAndActivoTrueAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(
                            producto.getCategoria().getId(), hoy, hoy
                    )
                    .map(Descuento_categoria::getPorcentaje)
                    .orElse(BigDecimal.ZERO);

            BigDecimal precioFinal = producto.getPrecio();

            // 3. Aplicar descuento por producto
            if (descuentoProducto.compareTo(BigDecimal.ZERO) > 0) {
                precioFinal = precioFinal.subtract(
                        precioFinal.multiply(descuentoProducto).divide(BigDecimal.valueOf(100))
                );
            }

            // 4. Aplicar descuento por categoría
            if (descuentoCategoria.compareTo(BigDecimal.ZERO) > 0) {
                precioFinal = precioFinal.subtract(
                        precioFinal.multiply(descuentoCategoria).divide(BigDecimal.valueOf(100))
                );
            }

            // 5. Actualizar stock
            producto.setStock(producto.getStock() - deto.getCantidad());
            productoRepository.save(producto);

            // 6. Crear detalle
            Detalle_venta detalle = detalleVentaMapper.toEntity(deto, producto, venta);

            // Guardar precio final con descuentos
            detalle.setPrecioDetalle(precioFinal);

            // 7. Subtotal
            BigDecimal subtotal = precioFinal.multiply(BigDecimal.valueOf(deto.getCantidad()));
            detalle.setSubtotal(subtotal);

            // 8. Agregar detalle
            venta.getDetalles().add(detalle);

            // 9. Sumar total
            totalVenta = totalVenta.add(subtotal);
        }


        // ===== ACTUALIZAR TOTAL DE LA VENTA =====
        venta.setTotal(totalVenta);

        // ===== GUARDAR VENTA COMPLETA =====
        Venta savedVenta = ventaRepository.save(venta);


        return mapper.toDTO(savedVenta);
    }




    @Transactional(readOnly = true)
    public VentaDTO obtenerPorId(Long id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new VentaNotFoundException("Venta no encontrada"));
        return mapper.toDTO(venta);
    }

    @Transactional(readOnly = true)
    public List<VentaDTO> obtenerPorSupermercado(Long supermercadoId) {
        if (!supermercadoRepository.existsById(supermercadoId)) {
            throw new SupermercadoNotFoundExepcion("Supermercado no encontrado");
        }

       List<Venta> ventas =ventaRepository.findBySupermercadoId(supermercadoId);
        return ventas.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public List<VentaDTO> obtenerPorAdministrador(Long administradorId, Long supermercadoId) {
        if (!supermercadoRepository.existsById(supermercadoId)) {
            throw new SupermercadoNotFoundExepcion("Supermercado no encontrado");
        }

        if (!administradorRepository.existsById(administradorId)) {
            throw new AdministradorNotFoundException("Administrador no encontrado");
        }

        return ventaRepository.findByAdministradorIdAndSupermercadoId(administradorId, supermercadoId)
                .stream()
                .map(mapper ::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<VentaDTO> obtenerPorCliente(Long clienteId, Long supermercadoId) {
        if (!supermercadoRepository.existsById(supermercadoId)) {
            throw new SupermercadoNotFoundExepcion("Supermercado no encontrado");
        }

        if (!clienteRepository.existsById(clienteId)) {
            throw new ClienteNotFoundException("Cliente no encontrado");
        }

        List<Venta> venta= ventaRepository.findByClienteIdAndSupermercadoId(clienteId, supermercadoId);
        return venta.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<VentaDTO> obtenerPorFechas(LocalDateTime desde, LocalDateTime hasta, Long supermercadoId) {
        if (!supermercadoRepository.existsById(supermercadoId)) {
            throw new SupermercadoNotFoundExepcion("Supermercado no encontrado");
        }

        return ventaRepository.findByFechaBetweenAndSupermercadoId(desde, hasta, supermercadoId)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<VentaDTO> obtenerPorTipoPago(Long tipoPagoId, Long supermercadoId) {
        if (!supermercadoRepository.existsById(supermercadoId)) {
            throw new SupermercadoNotFoundExepcion("Supermercado no encontrado");
        }

        if (!tipoPagoRepository.existsById(tipoPagoId)) {
            throw new VentaNotFoundException("Tipo de pago no encontrado");
        }

        return ventaRepository.findByTipoPagoIdAndSupermercadoId(tipoPagoId, supermercadoId)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public void eliminar(Long id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new VentaNotFoundException("Venta no encontrada"));

        ventaRepository.deleteById(id);
    }
}