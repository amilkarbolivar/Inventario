package com.inventario.service;

import com.inventario.dto.venta.VentaCreateDTO;
import com.inventario.dto.venta.VentaDTO;
import com.inventario.dto.detalleventa.DetalleVentaCreateDTO;
import com.inventario.exception.*;
import com.inventario.mapper.VentaMapper;
import com.inventario.model.*;
import com.inventario.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    public VentaDTO crear(VentaCreateDTO dto) {
        // Verificar que el supermercado existe
        Supermercado supermercado = supermercadoRepository.findById(dto.getSupermercadoId())
                .orElseThrow(() -> new SupermercadoNotFoundExepcion("Supermercado no encontrado"));

        // Verificar que el administrador existe y pertenece al supermercado
        Administrador administrador = administradorRepository.findById(dto.getAdministradorId())
                .orElseThrow(() -> new AdministradorNotFoundException("Administrador no encontrado"));

        if (!administrador.getSupermercado().getId().equals(dto.getSupermercadoId())) {
            throw new AdministradorNotFoundException("El administrador no pertenece al supermercado especificado");
        }

        // Verificar cliente si se proporciona
        Cliente cliente = null;
        if (dto.getClienteId() != null) {
            cliente = clienteRepository.findById(dto.getClienteId())
                    .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado"));

            if (!cliente.getSupermercado().getId().equals(dto.getSupermercadoId())) {
                throw new ClienteNotFoundException("El cliente no pertenece al supermercado especificado");
            }
        }

        // Verificar que el tipo de pago existe
        Tipo_pago tipoPago = tipoPagoRepository.findById(dto.getTipoPagoId())
                .orElseThrow(() -> new VentaNotFoundException("Tipo de pago no encontrado"));

        // Crear la venta
        Venta venta = new Venta();
        venta.setAdministrador(administrador);
        venta.setCliente(cliente);
        venta.setSupermercado(supermercado);
        venta.setTipoPago(tipoPago);
        venta.setFecha(LocalDateTime.now());
        venta.setTotal(BigDecimal.ZERO);

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