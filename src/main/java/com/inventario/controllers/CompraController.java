package com.inventario.controllers;

import com.inventario.dto.compra.CompraCreateDTO;
import com.inventario.dto.compra.CompraDTO;
import com.inventario.service.CompraService;
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
@RequestMapping("/api/compras")
@RequiredArgsConstructor
public class CompraController {

    private final CompraService compraService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CompraDTO> crear(@Valid @RequestBody CompraCreateDTO dto) {
        CompraDTO compra = compraService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(compra);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CompraDTO> obtenerPorId(@PathVariable Long id) {
        CompraDTO compra = compraService.obtenerPorId(id);
        return ResponseEntity.ok(compra);
    }

    @GetMapping("/supermercado/{supermercadoId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CompraDTO>> obtenerPorSupermercado(@PathVariable Long supermercadoId) {
        List<CompraDTO> compras = compraService.obtenerPorSupermercado(supermercadoId);
        return ResponseEntity.ok(compras);
    }

    @GetMapping("/supermercado/{supermercadoId}/administrador/{administradorId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CompraDTO>> obtenerPorAdministrador(
            @PathVariable Long supermercadoId,
            @PathVariable Long administradorId) {
        List<CompraDTO> compras = compraService.obtenerPorAdministrador(administradorId, supermercadoId);
        return ResponseEntity.ok(compras);
    }

    @GetMapping("/fechas")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CompraDTO>> obtenerPorFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta) {
        List<CompraDTO> compras = compraService.obtenerPorFechas(desde, hasta);
        return ResponseEntity.ok(compras);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        compraService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}