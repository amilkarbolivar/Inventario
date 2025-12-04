package com.inventario.controllers;

import com.inventario.dto.venta.VentaCreateDTO;
import com.inventario.dto.venta.VentaDTO;
import com.inventario.service.VentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VentaDTO> crear(@Valid @RequestBody VentaCreateDTO dto) {
        VentaDTO venta = ventaService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(venta);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VentaDTO> obtenerPorId(@PathVariable Long id) {
        VentaDTO venta = ventaService.obtenerPorId(id);
        return ResponseEntity.ok(venta);
    }

    @GetMapping("/supermercado/{supermercadoId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<VentaDTO>> obtenerPorSupermercado(@PathVariable Long supermercadoId) {
        List<VentaDTO> ventas = ventaService.obtenerPorSupermercado(supermercadoId);
        return ResponseEntity.ok(ventas);
    }

    @GetMapping("/supermercado/{supermercadoId}/administrador/{administradorId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<VentaDTO>> obtenerPorAdministrador(
            @PathVariable Long supermercadoId,
            @PathVariable Long administradorId) {
        List<VentaDTO> ventas = ventaService.obtenerPorAdministrador(administradorId, supermercadoId);
        return ResponseEntity.ok(ventas);
    }

    @GetMapping("/supermercado/{supermercadoId}/cliente/{clienteId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<VentaDTO>> obtenerPorCliente(
            @PathVariable Long supermercadoId,
            @PathVariable Long clienteId) {
        List<VentaDTO> ventas = ventaService.obtenerPorCliente(clienteId, supermercadoId);
        return ResponseEntity.ok(ventas);
    }

    @GetMapping("/supermercado/{supermercadoId}/fechas")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<VentaDTO>> obtenerPorFechas(
            @PathVariable Long supermercadoId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta) {
        List<VentaDTO> ventas = ventaService.obtenerPorFechas(desde, hasta, supermercadoId);
        return ResponseEntity.ok(ventas);
    }

    @GetMapping("/supermercado/{supermercadoId}/tipo-pago/{tipoPagoId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<VentaDTO>> obtenerPorTipoPago(
            @PathVariable Long supermercadoId,
            @PathVariable Long tipoPagoId) {
        List<VentaDTO> ventas = ventaService.obtenerPorTipoPago(tipoPagoId, supermercadoId);
        return ResponseEntity.ok(ventas);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        ventaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}