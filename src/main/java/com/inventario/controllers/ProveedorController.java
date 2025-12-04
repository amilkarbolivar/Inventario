package com.inventario.controllers;

import com.inventario.dto.proveedor.ProveedorCreateDTO;
import com.inventario.dto.proveedor.ProveedorDTO;
import com.inventario.dto.proveedor.ProveedorUpdateDTO;
import com.inventario.service.ProveedorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedor")
@RequiredArgsConstructor
public class ProveedorController {

    private final ProveedorService proveedorService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<ProveedorDTO> crear(@Valid @RequestBody ProveedorCreateDTO dto) {
        ProveedorDTO proveedor = proveedorService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(proveedor);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<ProveedorDTO> obtenerPorId(@PathVariable Long id) {
        ProveedorDTO proveedor = proveedorService.obtenerPorId(id);
        return ResponseEntity.ok(proveedor);
    }

    @GetMapping("/supermercado/{supermercadoId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<List<ProveedorDTO>> obtenerPorSupermercado(@PathVariable Long supermercadoId) {
        List<ProveedorDTO> proveedores = proveedorService.obtenerPorSupermercado(supermercadoId);
        return ResponseEntity.ok(proveedores);
    }

    @GetMapping("/supermercado/{supermercadoId}/correo/{correo}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<ProveedorDTO> obtenerPorCorreo(
            @PathVariable Long supermercadoId,
            @PathVariable String correo) {
        ProveedorDTO proveedor = proveedorService.obtenerPorCorreo(correo, supermercadoId);
        return ResponseEntity.ok(proveedor);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<ProveedorDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProveedorUpdateDTO dto) {
        ProveedorDTO proveedor = proveedorService.actualizar(id, dto);
        return ResponseEntity.ok(proveedor);
    }

    @DeleteMapping("/{id}/supermercado/{supermercadoId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id,
            @PathVariable Long supermercadoId) {
        proveedorService.eliminar(id, supermercadoId);
        return ResponseEntity.noContent().build();
    }
}